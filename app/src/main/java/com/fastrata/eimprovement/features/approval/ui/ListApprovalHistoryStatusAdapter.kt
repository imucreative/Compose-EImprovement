package com.fastrata.eimprovement.features.approval.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemListApprovalHistoryStatusBinding
import com.fastrata.eimprovement.features.approval.data.model.ApprovalHistoryStatusModel

class ListApprovalHistoryStatusAdapter : RecyclerView.Adapter<ListApprovalHistoryStatusAdapter.ApprovalViewHolder>() {

    private var list = ArrayList<ApprovalHistoryStatusModel?>()
    fun setList(data: ArrayList<ApprovalHistoryStatusModel?>?) {
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    inner class ApprovalViewHolder(private val binding: ItemListApprovalHistoryStatusBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ApprovalHistoryStatusModel) {
            binding.apply {
                pic.text = data.pic
                status.text = data.status
                comment.text = data.comment
                date.text = data.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApprovalViewHolder {
        val items = ItemListApprovalHistoryStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApprovalViewHolder(items)
    }

    override fun onBindViewHolder(holder: ApprovalViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}