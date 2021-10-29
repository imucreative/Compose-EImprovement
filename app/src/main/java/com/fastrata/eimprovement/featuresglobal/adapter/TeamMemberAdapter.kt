package com.fastrata.eimprovement.featuresglobal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemTeamMemberBinding
import com.fastrata.eimprovement.featuresglobal.data.model.TeamMemberItem

class TeamMemberAdapter: RecyclerView.Adapter<TeamMemberAdapter.TeamMemberViewHolder>() {

    private var list = ArrayList<TeamMemberItem?>()
    fun setListTeamMember(data: ArrayList<TeamMemberItem?>?) {
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    private lateinit var callback: TeamMemberCallback
    fun teamMemberCreateCallback(callback: TeamMemberCallback) {
        this.callback = callback
    }

    inner class TeamMemberViewHolder(private val binding: ItemTeamMemberBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TeamMemberItem) {

            binding.removeTeamMember.setOnClickListener {
                callback.removeClicked(data)
            }

            binding.apply {
                name.text = data.name?.name
                department.text = data.department?.department
                task.text = data.task?.task
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMemberAdapter.TeamMemberViewHolder {
        val items = ItemTeamMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamMemberViewHolder(items)
    }

    override fun onBindViewHolder(holder: TeamMemberAdapter.TeamMemberViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}