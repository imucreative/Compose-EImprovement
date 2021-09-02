package com.fastrata.eimprovement.features.projectimprovement.ui.create

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
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep8Binding
import com.fastrata.eimprovement.databinding.FragmentProjectImprovementStep9Binding
import com.fastrata.eimprovement.features.projectimprovement.adapter.PiCreateAttachmentAdapter
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjecImprovementCreateAttachmentCallback
import com.fastrata.eimprovement.features.projectimprovement.ui.ProjectImprovementViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.utils.SnackBarCustom
import timber.log.Timber

class ProjectImprovStep9Fragment : Fragment() {

    private  lateinit var _binding : FragmentProjectImprovementStep9Binding
    private val binding get() = _binding!!
    private val pickFromGallery = 101
    private lateinit var viewModel: ProjectImprovementViewModel
    private lateinit var adapter: PiCreateAttachmentAdapter
    private lateinit var uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectImprovementStep9Binding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProjectImprovementStep9Binding.bind(view)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            ProjectImprovementViewModel::class.java)

        initComponent()
    }

    private fun initComponent() {
        viewModel.setpiattachment()
        adapter = PiCreateAttachmentAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvPiAttachment.setHasFixedSize(true)
            rvPiAttachment.layoutManager = LinearLayoutManager(context)
            rvPiAttachment.adapter = adapter

            addAttachment.setOnClickListener {
                openFolder()
            }
        }

        adapter.piCreateCallback(object : ProjecImprovementCreateAttachmentCallback {
            override fun removeClicked(data: AttachmentItem) {
                Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()
            }

            override fun showAttachment(data: AttachmentItem) {
                println("### Testing show attachment : ${data.name}")
            }
        })
        viewModel.getpiattachment().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                Timber.i("### ambil dari getSuggestionSystemAttachment $it")
            }
        })

        setdata()
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

    private fun setdata() {
        binding.apply {
//            addAttachment.setOnClickListener {
//                if (fileName.text.isEmpty()) {
//                    SnackBarCustom.snackBarIconInfo(
//                        root.rootView, layoutInflater, resources, root.rootView.context,
//                        "Attachment must be fill before added",
//                        R.drawable.ic_close, R.color.red_500)
//                } else {
//
//                    val addData = AttachmentItem(
//                        name = initFileName,
//                        uri = uri.toString(),
//                        size = initFileSize
//                    )
//
//                    viewModel.addAttachment(addData, data?.attachment)
//
//                    fileName.text = ""
//                }
//            }
        }
    }
}