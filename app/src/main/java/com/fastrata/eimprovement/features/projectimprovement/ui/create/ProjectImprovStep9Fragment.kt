package com.fastrata.eimprovement.features.projectimprovement.ui.create

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep9Binding
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.ui.adapter.*
import com.fastrata.eimprovement.ui.model.AttachmentItem
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovStep9Fragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private  var _binding : FragmentProjectImprovementStep9Binding? = null
    private val binding get() = _binding!!
    private val pickFromGallery = 101
    private var data : ProjectImprovementCreateModel? = null
    private var piNo: String? = ""
    private var action: String? = ""
    private lateinit var viewModel: ProjectImprovementViewModel
    private lateinit var attachmentAdapter: AttachmentAdapter
    private lateinit var uri: Uri
    private lateinit var initFileSize: String
    private lateinit var initFileName: String
    private lateinit var initFilePath: String
    private var source: String = PI_CREATE
    private lateinit var ext : String
    private val fileNameExt = arrayOf(".JPEG", ".JPG",".PNG", ".PDF",".DOC","DOCX","XLS","XLSX")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep9Binding.inflate(layoutInflater, container, false)

        viewModel = injectViewModel(viewModelFactory)

        piNo = arguments?.getString(PI_DETAIL_DATA)
        action = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (piNo == "") PI_CREATE else PI_DETAIL_DATA

        data = HawkUtils().getTempDataCreatePi(source)
        viewModel.setAttachment(source)

        attachmentAdapter = AttachmentAdapter()
        attachmentAdapter.notifyDataSetChanged()

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
                openFolder()
            }
        }

        initList(data?.attachment)
        setData()
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

    private fun initList(attachment: ArrayList<AttachmentItem?>?) {
        attachmentAdapter.attachmentCreateCallback(object : AttachmentCallback {
            override fun removeClicked(data: AttachmentItem) {
                if (action != APPROVE) {
                    attachment?.remove(data)

                    viewModel.updateAttachment(attachment)
                    viewModel.getAttachment()
                        .observe(viewLifecycleOwner, {
                            if (it != null) {
                                attachmentAdapter.setList(it)
                            }
                        })
                }
            }

            override fun showAttachment(data: AttachmentItem) {
                println("### Testing show attachment : ${data.name}")
                println("### Testing path attachment : ${data.uri}")
                if (data.uri.isEmpty()){
                    println("### FILE EXIST : NOT EXIST")
                    SnackBarCustom.snackBarIconInfo(
                        binding.root, layoutInflater, resources, binding.root.context,
                        "File Attachment not from device",
                        R.drawable.ic_close, R.color.red_500)
                }else{
                    println("### FILE EXIST : EXIST")
                    val intent = Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT)
                    startActivityForResult(Intent.createChooser(intent, data.uri), 111)
                }
            }
        })

        viewModel.getAttachment().observe(viewLifecycleOwner, {
            if (it != null) {
                attachmentAdapter.setList(it)
            }
        })
    }

    private fun openFolder() {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("return-data", true)
        startActivityForResult(
            Intent.createChooser(intent, "Complete action using"),
            pickFromGallery
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickFromGallery && resultCode == Activity.RESULT_OK) {
            if (data != null) {
//                uri = data.data!!
//                initFileName = context?.let { FileInformation().getName(it, uri) }.toString()
//                initFileSize = context?.let { FileInformation().getSize(it, uri) }.toString()
//                initFilePath = context?.let { FileInformation().getPath(it, uri) }.toString()
//                binding.fileName.text = initFileName
//                Timber.e("### path uri : $uri")
//                Timber.e("### file size : $initFileSize")
//                Timber.e("### path : $initFilePath")
                uri = data.data!!
                val fileData = FileUtils().from(requireContext(),uri)
                val file_size: Int = java.lang.String.valueOf(fileData!!.length() / 1024).toInt()
                Timber.e("###FILE SIZE: $file_size")
                if (file_size == 0 || file_size >= 2048){
                    SnackBarCustom.snackBarIconInfo(
                        binding.root, layoutInflater, resources, binding.root.context,
                        "File size failed",
                        R.drawable.ic_close, R.color.red_500)
                }else{
                    initFileName = context?.let { FileInformation().getName(it, uri) }.toString()
                    initFileSize = context?.let { FileInformation().getSize(it, uri) }.toString()
                    initFilePath = context?.let { FileInformation().getPath(it, uri) }.toString()
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
                                "Attachmen failed",
                                R.drawable.ic_close, R.color.red_500)
                        }
                    }
                }
            }
        }
    }

    private fun setData() {
        binding.apply {
            addAttachment.setOnClickListener {
                if (fileName.text.isEmpty()) {
                    SnackBarCustom.snackBarIconInfo(
                        root, layoutInflater, resources, root.context,
                        "Attachment must be fill before added",
                        R.drawable.ic_close, R.color.red_500)
                } else {

                    val addData = AttachmentItem(
                        name = initFileName,
                        uri = uri.toString(),
                        size = initFileSize
                    )

                    viewModel.addAttachment(addData, data?.attachment)

                    fileName.text = ""
                }
            }
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
                            "Attachment must be fill before next",
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
                            statusImplementation = data?.statusImplementation,
                            identification = data?.identification,
                            target = data?.target,
                            sebabMasalah = data?.sebabMasalah,
                            akarMasalah = data?.akarMasalah,
                            nilaiOutput = data?.nilaiOutput,
                            nqi = data?.nqi,
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