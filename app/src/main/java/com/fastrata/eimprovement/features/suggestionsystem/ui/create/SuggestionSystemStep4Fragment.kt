package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.fastrata.eimprovement.databinding.FragmentSuggestionSystemStep4Binding
import com.fastrata.eimprovement.R
import com.google.android.material.button.MaterialButton
import android.content.Intent
import android.net.Uri

class SuggestionSystemStep4Fragment: Fragment() {

    private lateinit var fragment: FragmentSuggestionSystemStep4Binding
    var URI: Uri? = null
    private val PICK_FROM_GALLERY = 101

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentSuggestionSystemStep4Binding.inflate(layoutInflater, container, false)
        return fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
    }

    private fun initComponent() {
        fragment.apply {
            var counter = 1
            var layoutAttachment: View? = null

            addAttachment.setOnClickListener {
                layoutAttachment = layoutInflater.inflate(R.layout.item_suggestion_system_attachment, linearLayoutAttachment, false)
                val fileName = layoutAttachment?.findViewById<TextView>(R.id.file_name)

                fileName?.text = "File Lampiran $counter"
                linearLayoutAttachment.addView(layoutAttachment)
                counter += 1
            }

            val remove = layoutAttachment?.findViewById<MaterialButton>(R.id.remove_attachment)
            remove?.setOnClickListener {
                println("tes")
            }
        }
    }

    fun onDelete(view: View){
        //mLinearLayout.removeViewAt(1)
        fragment.linearLayoutAttachment.removeView(view)
    }

    fun openFolder() {
        val intent = Intent()
        intent.type = "*/*"
        //        intent.setType("file/*");
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("return-data", true)
        startActivityForResult(
            Intent.createChooser(intent, "Complete action using"),
            PICK_FROM_GALLERY
        )
    }

    //@SuppressLint("MissingSuperCall")
    /*fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            URI = data.data
            Tecket_Attach.setText(URI.getLastPathSegment())
        }
    }*/

}