package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep4Binding
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.di.Injectable
import com.fastrata.eimprovement.di.injectViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.utils.*
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class SuggestionSystemStep4Fragment: Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentSuggestionSystemStep4Binding? = null
    private val binding get() = _binding!!
    private val pickFromGallery = 101
    private var data: SuggestionSystemCreateModel? = null
    private var ssNo: String? = ""
    private var ssAction: String? = ""
    private lateinit var viewModelAttachment: SsCreateAttachmentViewModel
    private lateinit var adapter: SsCreateAttachmentAdapter
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
        _binding = FragmentSuggestionSystemStep4Binding.inflate(layoutInflater, container, false)

        viewModelAttachment = injectViewModel(viewModelFactory)

        ssNo = arguments?.getString(SS_DETAIL_DATA)
        ssAction = arguments?.getString(ACTION_DETAIL_DATA)

        source = if (ssNo == "") SS_CREATE else SS_DETAIL_DATA

        data = HawkUtils().getTempDataCreateSs(source)
        viewModelAttachment.setSuggestionSystemAttachment(source)

        adapter = SsCreateAttachmentAdapter()
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSuggestionSystemStep4Binding.bind(view)
        //viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SsCreateAttachmentViewModel::class.java)

        binding.apply {
            rvSsAttachment.setHasFixedSize(true)
            rvSsAttachment.layoutManager = LinearLayoutManager(context)
            rvSsAttachment.adapter = adapter

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
        adapter.ssCreateCallback(object : SuggestionSystemCreateAttachmentCallback {
            override fun removeClicked(data: AttachmentItem) {
                if (ssAction != APPROVE) {
                    Toast.makeText(context, data.name, Toast.LENGTH_LONG).show()

                    attachment?.remove(data)

                    viewModelAttachment.updateAttachment(attachment)
                    viewModelAttachment.getSuggestionSystemAttachment()
                        .observe(viewLifecycleOwner, {
                            if (it != null) {
                                adapter.setList(it)
                                Timber.i("### ambil dari getSuggestionSystemAttachment $it")
                            }
                        })
                }
            }

            override fun showAttachment(data: AttachmentItem) {
                //File(URI(uri.toString()))
                println("### Testing show attachment : ${data.name}")
            }
        })

        viewModelAttachment.getSuggestionSystemAttachment().observe(viewLifecycleOwner, {
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
        if (requestCode == pickFromGallery && resultCode == RESULT_OK) {
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

                    viewModelAttachment.addAttachment(addData, data?.attachment)

                    fileName.text = ""
                }
            }
        }
    }

    private fun setValidation() {
        (activity as SuggestionSystemCreateWizard).setSsCreateCallback(object : SuggestionSystemCreateCallback {
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