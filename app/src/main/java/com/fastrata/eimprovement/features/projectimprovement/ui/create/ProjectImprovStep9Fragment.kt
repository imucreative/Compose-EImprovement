package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.stetho.server.http.HttpStatus
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep9Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.featuresglobal.adapter.*
import com.fastrata.eimprovement.featuresglobal.data.model.AttachmentItem
import com.fastrata.eimprovement.featuresglobal.viewmodel.AttachmentViewModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class ProjectImprovStep9Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private  var _binding : FragmentProjectImprovementStep9Binding? = null
    private val binding get() = _binding!!
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    private var userName: String = ""
    private lateinit var attachmentAdapter: AttachmentAdapter
    private lateinit var piCreateAttachmentViewModel: ProjectImprovementViewModel
    private lateinit var attachmentViewModel: AttachmentViewModel
    private lateinit var uri: Uri
    private lateinit var initFileSize: String
    private lateinit var initFileName: String
    private lateinit var initFilePath: String
    private var source: String = PI_CREATE
    private lateinit var ext : String
    private val fileNameExt = arrayOf(".JPEG", ".JPG",".PNG", ".PDF",".DOC","DOCX","XLS","XLSX")
    private lateinit var notification: HelperNotification
    private lateinit var selectedAttachmentItem: AttachmentItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep9Binding.inflate(layoutInflater, container, false)

        piCreateAttachmentViewModel = injectViewModel(viewModelFactory)
        attachmentViewModel = injectViewModel(viewModelFactory)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)
        piCreateAttachmentViewModel.setAttachment(source)

        userName = HawkUtils().getDataLogin().USER_NAME

        attachmentAdapter = AttachmentAdapter()
        attachmentAdapter.notifyDataSetChanged()

        notification = HelperNotification()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep9Binding.bind(view)

        binding.apply {
            rvPiAttachment.setHasFixedSize(true)
            rvPiAttachment.layoutManager = LinearLayoutManager(context)
            rvPiAttachment.adapter = attachmentAdapter

            getAttachment.setOnClickListener {
                val permission = ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                    )
                } else {
                    openFolder()
                }
            }

            addAttachment.setOnClickListener {
                when {
                    fileName.text.isEmpty() -> {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.file_empty),
                            R.drawable.ic_close, R.color.red_500
                        )
                    }
                    else -> {
                        uploadAttachment(uri)
                    }
                }
            }
        }

        initList(data?.attachment)
        setValidation()

        if (action == APPROVE) {
            disableForm()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun disableForm() {
        binding.apply {
            getAttachment.isClickable = false
            addAttachment.isClickable = false
        }
    }

    private fun initList(listAttachment: ArrayList<AttachmentItem?>?) {
        attachmentAdapter.attachmentCreateCallback(object : AttachmentCallback {
            override fun removeClicked(data: AttachmentItem) {
                if (action != APPROVE) {
                    activity?.let { activity ->
                        notification.shownotificationyesno(
                            activity,
                            resources.getString(R.string.delete),
                            resources.getString(R.string.delete_confirmation_file_attachment),
                            object : HelperNotification.CallBackNotificationYesNo {
                                override fun onNotificationNo() {

                                }

                                override fun onNotificationYes() {
                                    selectedAttachmentItem = data
                                    removeAttachment(listAttachment)
                                }
                            }
                        )
                    }
                }
            }

            override fun showAttachment(data: AttachmentItem) {
                println("### Testing show attachment : ${data.name}")
                println("### Testing path attachment : ${data.fileLocation}")
                /*if (data.fileLocation.isEmpty()){
                    println("### FILE EXIST : NOT EXIST")
                    SnackBarCustom.snackBarIconInfo(
                        binding.root, layoutInflater, resources, binding.root.context,
                        resources.getString(R.string.file_not_found),
                        R.drawable.ic_close, R.color.red_500)
                }else{
                    println("### FILE EXIST : EXIST")
                    val intent = Intent()*/
                //        .setType("*/*")
                /*        .setAction(Intent.ACTION_GET_CONTENT)
                    startActivityForResult(Intent.createChooser(intent, data.fileLocation), 111)
                }*/
            }
        })

        piCreateAttachmentViewModel.getAttachment().observe(viewLifecycleOwner, {
            if (it != null) {
                attachmentAdapter.setList(it)
            }
        })
    }

    private fun openFolder() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT

        /*
        val mimeTypes = arrayOf("image/bmp", "image/jpeg", "image/jpg", "image/png", "text/comma-separated-values",
        "application/msword", "application/pdf", "text/plain","application/vnd.ms-excel", "text/csv",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        */
        intent.putExtra("return-data", true)
        startActivityForResult(
            Intent.createChooser(intent, "Complete action using"),
            FILE_PICKER_REQUEST_CODE
        )
        //        Intent(Intent.ACTION_PICK).also {
        //            it.type = "image/*"
        //            val mimeTypes = arrayOf("image/jpeg", "image/png")
        //            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        //            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        //        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.data as Uri

                val fileData = FileUtils.getFile(requireContext(), uri)
                val fileSize: Int = java.lang.String.valueOf(fileData!!.length() / 1024).toInt()
                Timber.e("###FILE SIZE: $fileSize")
                if (fileSize == 0 || fileSize >= 2048){
                    SnackBarCustom.snackBarIconInfo(
                        binding.root, layoutInflater, resources, binding.root.context,
                        resources.getString(R.string.file_size),
                        R.drawable.ic_close, R.color.red_500)
                }else{
                    initFileName = FileInformation().getName(requireContext(), uri).toString()
                    initFileSize = FileInformation().getSize(requireContext(), uri).toString()
                    initFilePath = FileInformation().getPath(requireContext(), uri).toString()
                    if(initFileName.contains(".")){
                        ext = initFileName.substring(initFileName.lastIndexOf("."))
                        Timber.e("###EXT : $ext")
                        val match = fileNameExt.filter { ext.contains(it,ignoreCase = true) }
                        Timber.e("### MATCH SIZE: ${match.size}")
                        if (match.isNotEmpty()){
                            binding.fileName.text = initFileName
                            Timber.e("### path uri : $uri")
                            Timber.e("### file size : $initFileSize")
                            Timber.e("### path : $initFilePath")
                        }else{
                            SnackBarCustom.snackBarIconInfo(
                                binding.root, layoutInflater, resources, binding.root.context,
                                resources.getString(R.string.file_ext),
                                R.drawable.ic_close, R.color.red_500)
                        }
                    }
                }
            }
        }
    }

    private fun setDataAttachmentToHawk(id: Int, type: String, fileLocation: String) {
        binding.apply {
            val addData = AttachmentItem(
                id = id,
                name = initFileName,
                type = type,
                group = PROPOSAL,
                createdBy = userName,
                fileLocation = fileLocation
            )

            piCreateAttachmentViewModel.addAttachment(addData, data?.attachment, data, source)
            fileName.text = ""
        }
    }

    private fun removeDataAttachmentFromHawk(listAttachment: ArrayList<AttachmentItem?>?) {
        listAttachment?.remove(selectedAttachmentItem)

        piCreateAttachmentViewModel.updateAttachment(listAttachment, data, source)
        piCreateAttachmentViewModel.getAttachment().observe(viewLifecycleOwner, {
            if (it != null) {
                attachmentAdapter.setList(it)
            }
        })
    }

    private fun uploadAttachment(imageUri: Uri?) {
        val file: File = FileUtils.getFile(requireContext(), imageUri)
        val requestBodyFile: RequestBody = RequestBody.create("*/*".toMediaType(), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file_images", file.name, requestBodyFile)

        //val descriptionString = "Capture photo file desc"
        //val description = RequestBody.create(MultipartBody.FORM, descriptionString)

        attachmentViewModel.file = body
        attachmentViewModel.type = PI
        attachmentViewModel.group = PROPOSAL
        attachmentViewModel.createdBy = userName

        try {
            attachmentViewModel.processSubmitAttachment()

            attachmentViewModel.doSubmitAttachment.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner, { result ->
                    Timber.e("### -- $result")
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(requireContext(),"",false)
                                Timber.d("###-- Loading get upload loading")
                            }
                            Result.Status.SUCCESS -> {
                                val response = result.data
                                if (response?.code == HttpStatus.HTTP_OK) {
                                    HelperLoading.hideLoading()

                                    setDataAttachmentToHawk(response.data[0].id, response.data[0].type, response.data[0].fileLocation)
                                }

                                Timber.d("###-- Success get Upload sukses $response")
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Timber.d("###-- Error get Upload Error $result")
                            }
                        }
                    }
                })
            }
        } catch (err: Exception) {
            Snackbar.make(
                binding.root,
                "Error doSubmitAttachment : ${err.message}",
                Snackbar.LENGTH_SHORT
            ).show()
            Timber.e("### Error doSubmitAttachment : ${err.message}")
        }
    }

    private fun removeAttachment(listAttachment: ArrayList<AttachmentItem?>?) {
        try {
            attachmentViewModel.processRemoveAttachment(selectedAttachmentItem.id, selectedAttachmentItem.name, SS)

            attachmentViewModel.doRemoveAttachment.observeEvent(this) { resultObserve ->
                resultObserve.observe(viewLifecycleOwner, { result ->
                    Timber.e("### -- $result")
                    if (result != null) {
                        when (result.status) {
                            Result.Status.LOADING -> {
                                HelperLoading.displayLoadingWithText(requireContext(),"",false)
                                Timber.d("###-- Loading get doRemoveAttachment loading")
                            }
                            Result.Status.SUCCESS -> {

                                HelperLoading.hideLoading()

                                removeDataAttachmentFromHawk(listAttachment)

                                result.data?.let {
                                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                                    Timber.d("###-- Success get doRemoveAttachment sukses $it")
                                }
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Snackbar.make(binding.root, result.data?.message.toString(), Snackbar.LENGTH_SHORT).show()
                                Timber.d("###-- Error get doRemoveAttachment Error ${result.data}")
                            }
                        }
                    }
                })
            }
        } catch (err: Exception) {
            Snackbar.make(
                binding.root,
                "Error doRemoveAttachment : ${err.message}",
                Snackbar.LENGTH_SHORT
            ).show()
            Timber.e("### Error doRemoveAttachment : ${err.message}")
        }
    }

    private fun setValidation() {
        (activity as ProjectImprovementCreateWizard).setPiCreateCallback(object :
            ProjectImprovementSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean

                binding.apply {
                    stat = if (data?.attachment?.size == 0) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.file_empty),
                            R.drawable.ic_close, R.color.red_500)
                        false
                    } else {
                        HawkUtils().setTempDataCreatePi(
                            id = data?.id,
                            piNo = data?.piNo,
                            date = data?.date,
                            title = data?.title,
                            branch = data?.branch,
                            subBranch = data?.subBranch,
                            department = data?.department,
                            years = data?.years,
                            statusImplementationModel = data?.statusImplementationModel,
                            identification = data?.identification,
                            target = data?.target,
                            sebabMasalah = data?.sebabMasalah,
                            akarMasalah = data?.akarMasalah,
                            nilaiOutput = data?.nilaiOutput,
                            nqiModel = data?.nqiModel,
                            teamMember = data?.teamMember,
                            categoryFixing = data?.categoryFixing,
                            hasilImplementasi = data?.implementationResult,
                            attachment = data?.attachment,
                            statusProposal = data?.statusProposal,
                            source = source
                        )
                        true
                    }
                }

                return stat
            }
        })
    }
}