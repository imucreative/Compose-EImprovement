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
import com.fastrata.eimprovement.features.projectimprovement.adapter.PiCreateAttachmentAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjecImprovementCreateAttachmentCallback
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjectImprovementSystemCreateCallback
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateAttachmentCallback
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateCallback
import com.fastrata.eimprovement.features.suggestionsystem.ui.create.SuggestionSystemCreateWizard
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
    private var ssNo: String? = ""
    private var ssAction: String? = ""
    private lateinit var viewModel: ProjectImprovementViewModel
    private lateinit var adapter: PiCreateAttachmentAdapter
    private lateinit var uri: Uri
    private lateinit var initFileSize: String
    private lateinit var initFileName: String
    private lateinit var initFilePath: String
    private var source: String = SS_CREATE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep9Binding.inflate(layoutInflater, container, false)

        viewModel = injectViewModel(viewModelFactory)

        data = HawkUtils().getTempDataCreatePi()
        viewModel.setAttachment()

        adapter = PiCreateAttachmentAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep9Binding.bind(view)

        binding.apply {
            rvPiAttachment.setHasFixedSize(true)
            rvPiAttachment.layoutManager = LinearLayoutManager(context)
            rvPiAttachment.adapter = adapter

            getAttachment.setOnClickListener {
                openFolder()
            }
        }

        initList(data?.attachment)
        setData()
        setValidation()

        if (ssAction == APPROVE) {
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
        adapter.piCreateCallback(object : ProjecImprovementCreateAttachmentCallback {
            override fun removeClicked(data: AttachmentItem) {
                if (ssAction != APPROVE) {
                    Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()

                    attachment?.remove(data)

                    viewModel.updateAttachment(attachment)
                    viewModel.getAttachment()
                        .observe(viewLifecycleOwner, {
                            if (it != null) {
                                adapter.setList(it)
                            }
                        })
                }
            }

            override fun showAttachment(data: AttachmentItem) {
                //File(URI(uri.toString()))
                println("### Testing show attachment : ${data.name}")
            }
        })

        viewModel.getAttachment().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                Timber.i("### ambil dari getSuggestionSystemAttachment $it")
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
                uri = data.data!!
                initFileName = context?.let { FileInformation().getName(it, uri) }.toString()
                initFileSize = context?.let { FileInformation().getSize(it, uri) }.toString()
                initFilePath = context?.let { FileInformation().getPath(it, uri) }.toString()

                //if (initFileSize.toInt() <= 2048) {
                binding.fileName.text = initFileName

                Timber.e("### path uri : $uri")
                Timber.e("### file size : $initFileSize")
                Timber.e("### path : $initFilePath")
                /*} else {
                    SnackBarCustom.snackBarIconInfo(
                        binding.root.rootView, layoutInflater, resources, binding.root.rootView.context,
                        "Attachment must be under 2Mb",
                        R.drawable.ic_close, R.color.red_500)
                }*/
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
                        true
                    }
                }

                return stat
            }
        })
    }
}