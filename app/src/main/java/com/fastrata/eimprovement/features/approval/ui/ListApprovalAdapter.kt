package com.fastrata.eimprovement.features.approval.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemListApprovalBinding
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel

class ListApprovalAdapter : RecyclerView.Adapter<ListApprovalAdapter.ApprovalViewHolder>() {

    private var list = ArrayList<ApprovalModel>()

    fun setList(data: ArrayList<ApprovalModel>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var listApprovalCallback: ListApprovalCallback
    fun setApprovalCallback(listApprovalCallback: ListApprovalCallback) {
        this.listApprovalCallback = listApprovalCallback
    }

    inner class ApprovalViewHolder (private val binding: ItemListApprovalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ApprovalModel) {

            binding.root.setOnClickListener {
                listApprovalCallback.onItemClicked(data)
            }

            binding.apply {
                typeNo.text = data.typeNo
                title.text = data.title
                status.text = data.status
                branch.text = data.branch
                subBranch.text = data.subBranch
                type.text = data.type
                name.text = data.name
                date.text = data.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApprovalViewHolder {
        val items = ItemListApprovalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApprovalViewHolder(items)
    }

    override fun onBindViewHolder(holder: ApprovalViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}