package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.stetho.server.http.HttpStatus
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.adapter.AttachmentAdapter
import com.fastrata.eimprovement.featuresglobal.adapter.AttachmentCallback
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
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.fastrata.eimprovement.BuildConfig
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep5Binding
import com.fastrata.eimprovement.featuresglobal.data.model.DownloadResult
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.collect

class SuggestionSystemStep5Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemStep5Binding? = null
    private val binding get() = _binding!!
    private var data: SuggestionSystemCreateModel? = null
    private var ssNo: String? = ""
    private var ssAction: String? = ""
    private var userName: String = ""
    private lateinit var ssCreateAttachmentViewModel: SsCreateAttachmentViewModel
    private lateinit var attachmentViewModel: AttachmentViewModel
    private lateinit var attachmentAdapter: AttachmentAdapter
    private lateinit var uri: Uri
    private lateinit var initFileSize: String
    private lateinit var initFileName: String
    private lateinit var initFilePath: String
    private var source: String = SS_CREATE
    private lateinit var ext : String
    private lateinit var notification: HelperNotification
    private lateinit var selectedAttachmentItem: AttachmentItem
    private var fileUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuggestionSystemStep5Binding.inflate(layoutInflater, container, false)

        ssCreateAttachmentViewModel = injectViewModel(viewModelFactory)
        attachmentViewModel = injectViewModel(viewModelFactory)

        ssNo = arguments?.getString(SS_DETAIL_DATA)
        ssAction = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (ssNo == "") SS_CREATE else SS_DETAIL_DATA

        data = HawkUtils().getTempDataCreateSs(source)
        ssCreateAttachmentViewModel.setSuggestionSystemAttachment(source)

        userName = HawkUtils().getDataLogin().USER_NAME

        attachmentAdapter = AttachmentAdapter()
        attachmentAdapter.notifyDataSetChanged()

        notification = HelperNotification()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep5Binding.bind(view)

        if (hasPermissions(context, PERMISSIONS)) {
            binding.apply {
                rvSsAttachment.setHasFixedSize(true)
                rvSsAttachment.layoutManager = LinearLayoutManager(context)
                rvSsAttachment.adapter = attachmentAdapter

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
                        fileName.text.isNullOrEmpty() -> {
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

            setValidation()

            if ((ssAction == APPROVE) || (ssAction == DETAIL)) {
                disableForm()
            }

            initList(data?.attachment)
        } else {
            requestPermissions(PERMISSIONS.toTypedArray(), FILE_PICKER_REQUEST_CODE)
        }
    }

    private fun hasPermissions(context: Context?, permissions: List<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            return permissions.all { permission ->
                ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == FILE_PICKER_REQUEST_CODE && hasPermissions(context, PERMISSIONS)) {
            initList(data?.attachment)
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
                if ((ssAction != APPROVE)&&(ssAction != DETAIL)) {
                    activity?.let { activity ->
                        notification.showNotificationYesNo(
                            activity,
                            requireContext(),
                            R.color.blue_500,
                            resources.getString(R.string.delete),
                            resources.getString(R.string.delete_confirmation_file_attachment),
                            resources.getString(R.string.ok),
                            resources.getString(R.string.no),
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
                println("### Testing show attachment : $data")

                if (data.id != 0) {
                    fileUrl = data.fileLocation
                    val name = data.name
                    val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    val file = File(folder, name)
                    val uri = context?.let {
                        FileProvider.getUriForFile(it, "${BuildConfig.APPLICATION_ID}.provider", file)
                    }
                    val extension = MimeTypeMap.getFileExtensionFromUrl(uri?.path)
                    val type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)

                    println("uri : $uri")
                    println("fileUrl : $fileUrl")
                    println("type : $type")

                    /*val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                    intent.setDataAndType(uri, type)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    intent.putExtra(Intent.EXTRA_TITLE, name)
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    startActivityForResult(intent, DOWNLOAD_FILE_CODE)*/

                    if (uri != null) {
                        context?.let { downloadFile(it, fileUrl, uri) }
                    }

                }
            }
        })

        ssCreateAttachmentViewModel.getSuggestionSystemAttachment().observe(viewLifecycleOwner, {
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
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    uri = data.data as Uri

                    val fileData = FileUtils.getFile(requireContext(), uri)
                    val fileSize: Int = fileData.length().toInt()
                    Timber.e("### FILE SIZE: $fileSize")

                    if (fileSize == 0 || (fileData.length() / 1024) >= 2048) {
                        SnackBarCustom.snackBarIconInfo(
                            binding.root, layoutInflater, resources, binding.root.context,
                            resources.getString(R.string.file_size),
                            R.drawable.ic_close, R.color.red_500
                        )
                    } else {
                        initFileName = FileInformation().getName(requireContext(), uri).toString()
                        initFileSize = FileInformation().getSize(requireContext(), uri).toString()
                        initFilePath = FileInformation().getPath(requireContext(), uri).toString()
                        if (initFileName.contains(".")) {
                            ext = initFileName.substring(initFileName.lastIndexOf("."))
                            Timber.e("###EXT : $ext")
                            val match = FILE_NAME_EXT.filter { ext.contains(it, ignoreCase = true) }
                            Timber.e("### MATCH SIZE: ${match.size}")
                            if (match.isNotEmpty()) {
                                binding.fileName.text = initFileName
                                Timber.e("### path uri : $uri")
                                Timber.e("### file size : $initFileSize")
                                Timber.e("### path : $initFilePath")
                            } else {
                                SnackBarCustom.snackBarIconInfo(
                                    binding.root, layoutInflater, resources, binding.root.context,
                                    resources.getString(R.string.file_ext),
                                    R.drawable.ic_close, R.color.red_500
                                )
                            }
                        }
                    }
                }
            } else if (requestCode == DOWNLOAD_FILE_CODE && resultCode == Activity.RESULT_OK) {
                data?.data?.let { uri ->
                    context?.let { context ->
                        downloadFile(context, fileUrl, uri)
                    }
                }
            }
        }
    }

    private fun downloadFile(context: Context, url: String, file: Uri) {
        val ktor = HttpClient(Android)

        attachmentViewModel.setDownloading(true)
        context.contentResolver.openOutputStream(file)?.let { outputStream ->
            CoroutineScope(Dispatchers.IO).launch {
                ktor.downloadFile(outputStream, url).collect {
                    withContext(Dispatchers.Main) {
                        binding.apply {
                            when (it) {
                                is DownloadResult.Success -> {
                                    try {
                                        attachmentViewModel.setDownloading(false)
                                        progress.progress = 0
                                        progress.visibility = View.GONE

                                        Toast.makeText(context, "Download Complete", Toast.LENGTH_LONG).show()
                                        val viewFile = FileInformation().viewFile(context, file)
                                        startActivity(viewFile)

                                        /*val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                                        //intent.setDataAndType(uri, data.type)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        //intent.putExtra(Intent.EXTRA_TITLE, name)
                                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                                        intent.addCategory(Intent.CATEGORY_OPENABLE)
                                        startActivityForResult(intent, DOWNLOAD_FILE_CODE)*/
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error : ${e.message}", Toast.LENGTH_LONG).show()
                                        Timber.e("### Error : ${e.message}")
                                    }
                                }

                                is DownloadResult.Error -> {
                                    progress.visibility = View.GONE
                                    attachmentViewModel.setDownloading(false)

                                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                }

                                is DownloadResult.Progress -> {
                                    progress.visibility = View.VISIBLE
                                    progress.progress = it.progress
                                }
                            }
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
                group = if(conditionImplementation()) IMPLEMENT else PROPOSAL,
                createdBy = userName,
                fileLocation = fileLocation
            )

            ssCreateAttachmentViewModel.addAttachment(addData, data?.attachment, data, source)
            fileName.text = ""
        }
    }

    private fun conditionImplementation(): Boolean {
        return when (data?.statusProposal?.id) {
            6, 9 -> {
                true
            }
            else -> {
                false
            }
        }
    }

    private fun removeDataAttachmentFromHawk(listAttachment: ArrayList<AttachmentItem?>?) {
        listAttachment?.remove(selectedAttachmentItem)

        ssCreateAttachmentViewModel.updateAttachment(listAttachment, data, source)
        ssCreateAttachmentViewModel.getSuggestionSystemAttachment().observe(viewLifecycleOwner, {
            if (it != null) {
                attachmentAdapter.setList(it)
            }
        })
    }

    private fun uploadAttachment(imageUri: Uri?) {
        val file: File = FileUtils.getFile(requireContext(), imageUri)
        val requestBodyFile: RequestBody = RequestBody.create("*/*".toMediaType(), file)
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("file_images", file.name, requestBodyFile)

        attachmentViewModel.file = body
        attachmentViewModel.type = SS
        attachmentViewModel.group = if(conditionImplementation()) IMPLEMENT else PROPOSAL
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
                                Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
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
                                    SnackBarCustom.snackBarIconInfo(
                                        binding.root, layoutInflater, resources, binding.root.context,
                                        it.message,
                                        R.drawable.ic_close, R.color.red_500)
                                    Timber.d("###-- Success get doRemoveAttachment sukses $it")
                                }
                            }
                            Result.Status.ERROR -> {
                                HelperLoading.hideLoading()
                                Toast.makeText(requireContext(),"Error : ${result.message}", Toast.LENGTH_LONG).show()
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
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
            override fun onDataPass(): Boolean {
                var stat: Boolean

                binding.apply {
                    stat = if (data?.attachment?.size == 0 && (ssAction != DETAIL && conditionImplementation())) {
                        SnackBarCustom.snackBarIconInfo(
                            root, layoutInflater, resources, root.context,
                            resources.getString(R.string.file_empty),
                            R.drawable.ic_close, R.color.red_500)
                        false
                    } else {
                        true
                    }

                    HawkUtils().setTempDataCreateSs(
                        ssNo = data?.ssNo,
                        date = data?.date,
                        title = data?.title,
                        listCategory = data?.categoryImprovement,
                        name = data?.name,
                        nik = data?.nik,
                        branchCode = data?.branchCode,
                        branch = data?.branch,
                        subBranch = data?.subBranch,
                        department = data?.department,
                        directMgr = data?.directMgr,
                        suggestion = data?.suggestion,
                        problem = data?.problem,
                        statusImplementation = data?.statusImplementation,
                        teamMember = data?.teamMember,
                        attachment = data?.attachment,
                        statusProposal = data?.statusProposal,
                        headId = data?.headId,
                        userId = data?.userId,
                        orgId = data?.orgId,
                        warehouseId = data?.warehouseId,
                        proses = data?.proses,
                        result = data?.result,
                        historyApproval = data?.historyApproval,
                        activityType = data?.activityType,
                        submitType = data?.submitType,
                        comment = data?.comment,
                        source = source
                    )
                }

                return stat
            }
        })
    }
}