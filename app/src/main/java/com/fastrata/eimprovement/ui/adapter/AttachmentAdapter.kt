package com.fastrata.eimprovement.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemAttachmentBinding
import com.fastrata.eimprovement.ui.model.AttachmentItem

class AttachmentAdapter: RecyclerView.Adapter<AttachmentAdapter.AttachmentViewHolder>() {

    private var list = ArrayList<AttachmentItem?>()
    fun setList(data: ArrayList<AttachmentItem?>?) {
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    private lateinit var callback: AttachmentCallback
    fun attachmentCreateCallback(callback: AttachmentCallback) {
        this.callback = callback
    }

    inner class AttachmentViewHolder(private val binding: ItemAttachmentBinding): RecyclerView.ViewHolder(binding.root)  {
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
        val items = ItemAttachmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttachmentViewHolder(items)
    }

    override fun onBindViewHolder(holder: AttachmentViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}