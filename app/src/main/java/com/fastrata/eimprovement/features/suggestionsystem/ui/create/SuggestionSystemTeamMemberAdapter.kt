package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemSuggestionSystemTeamMemberBinding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemTeamMemberModel

class SuggestionSystemTeamMemberAdapter : RecyclerView.Adapter<SuggestionSystemTeamMemberAdapter.SuggestionSystemTeamMemberViewHolder>() {

    private var list = ArrayList<SuggestionSystemTeamMemberModel>()

    fun setList(data: ArrayList<SuggestionSystemTeamMemberModel>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var suggestionSystemTeamMemberCallback: SuggestionSystemTeamMemberCallback
    fun setSuggestionSystemTeamMemberCallback(suggestionSystemTeamMemberCallback: SuggestionSystemTeamMemberCallback) {
        this.suggestionSystemTeamMemberCallback = suggestionSystemTeamMemberCallback
    }

    inner class SuggestionSystemTeamMemberViewHolder(private val binding: ItemSuggestionSystemTeamMemberBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SuggestionSystemTeamMemberModel) {

            binding.removeTeamMember.setOnClickListener {
                suggestionSystemTeamMemberCallback.removeClicked(data)
            }

            binding.apply {
                name.text = data.name
                department.text = data.department
                task.text = data.task
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionSystemTeamMemberViewHolder {
        val items = ItemSuggestionSystemTeamMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuggestionSystemTeamMemberViewHolder(items)
    }

    override fun onBindViewHolder(holder: SuggestionSystemTeamMemberViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

interface SuggestionSystemTeamMemberCallback {
    fun removeClicked(data: SuggestionSystemTeamMemberModel)
}