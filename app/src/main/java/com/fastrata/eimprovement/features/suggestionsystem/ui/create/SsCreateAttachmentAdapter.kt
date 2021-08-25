package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemSuggestionSystemAttachmentBinding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem

class SsCreateAttachmentAdapter: RecyclerView.Adapter<SsCreateAttachmentAdapter.AttachmentViewHolder>() {

    private var list = ArrayList<AttachmentItem?>()
    fun setList(data: ArrayList<AttachmentItem?>?) {
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    private lateinit var callback: SuggestionSystemCreateAttachmentCallback
    fun ssCreateCallback(callback: SuggestionSystemCreateAttachmentCallback) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentViewHolder {
        val items = ItemSuggestionSystemAttachmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttachmentViewHolder(items)
    }

    override fun onBindViewHolder(holder: AttachmentViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}