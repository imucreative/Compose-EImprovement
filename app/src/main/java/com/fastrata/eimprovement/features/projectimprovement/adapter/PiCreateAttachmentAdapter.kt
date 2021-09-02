package com.fastrata.eimprovement.features.projectimprovement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemSuggestionSystemAttachmentBinding
import com.fastrata.eimprovement.features.projectimprovement.callback.ProjecImprovementCreateAttachmentCallback
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem

class PiCreateAttachmentAdapter: RecyclerView.Adapter<PiCreateAttachmentAdapter.AttachmentViewHolder>() {

    private var list = ArrayList<AttachmentItem?>()
    fun setList(data: ArrayList<AttachmentItem?>?) {
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    private lateinit var callback: ProjecImprovementCreateAttachmentCallback
    fun piCreateCallback(callback: ProjecImprovementCreateAttachmentCallback) {
        this.callback = callback
    }

    inner class AttachmentViewHolder(private val binding: ItemSuggestionSystemAttachmentBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(data: AttachmentItem) {
            binding.apply {
                removeAttachment.setOnClickListener {
                    callback.removeClicked(data)
                }

                root.setOnClickListener {
                    callback.showAttachment(data)
                }

                fileName.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PiCreateAttachmentAdapter.AttachmentViewHolder {
        val items = ItemSuggestionSystemAttachmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttachmentViewHolder(items)
    }

    override fun onBindViewHolder(holder: PiCreateAttachmentAdapter.AttachmentViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}