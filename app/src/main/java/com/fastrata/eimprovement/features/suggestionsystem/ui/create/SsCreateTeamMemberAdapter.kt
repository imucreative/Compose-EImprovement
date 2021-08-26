package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemSuggestionSystemTeamMemberBinding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem

class SsCreateTeamMemberAdapter : RecyclerView.Adapter<SsCreateTeamMemberAdapter.TeamMemberViewHolder>() {

    private var list = ArrayList<TeamMemberItem?>()
    fun setListTeamMember(data: ArrayList<TeamMemberItem?>?) {
        list.clear()
        if (data != null) {
            list.addAll(data)
        }
        notifyDataSetChanged()
    }

    private lateinit var callback: SuggestionSystemCreateTeamMemberCallback
    fun ssCreateCallback(callback: SuggestionSystemCreateTeamMemberCallback) {
        this.callback = callback
    }

    inner class TeamMemberViewHolder(private val binding: ItemSuggestionSystemTeamMemberBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TeamMemberItem) {

            binding.removeTeamMember.setOnClickListener {
                callback.removeClicked(data)
            }

            binding.apply {
                name.text = data.name
                department.text = data.department
                task.text = data.task
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamMemberViewHolder {
        val items = ItemSuggestionSystemTeamMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamMemberViewHolder(items)
    }

    override fun onBindViewHolder(holder: TeamMemberViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}