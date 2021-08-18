package com.fastrata.eimprovement.features.suggestionsystem.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemSuggestionSystemBinding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel

class SuggestionSystemAdapter : RecyclerView.Adapter<SuggestionSystemAdapter.SuggestionSystemViewHolder>() {

    private var list = ArrayList<SuggestionSystemModel>()

    fun setList(data: ArrayList<SuggestionSystemModel>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    private lateinit var suggestionSystemCallback: SuggestionSystemCallback
    fun setSuggestionSystemCallback(suggestionSystemCallback: SuggestionSystemCallback) {
        this.suggestionSystemCallback = suggestionSystemCallback
    }

    inner class SuggestionSystemViewHolder(private val binding: ItemSuggestionSystemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SuggestionSystemModel) {

            binding.root.setOnClickListener {
                suggestionSystemCallback.onItemClicked(data)
            }

            binding.apply {
                ssNo.text = data.ssNo
                date.text = data.date
                title.text = data.title
                status.text = data.status
                branch.text = data.branch
                subBranch.text = data.subBranch
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionSystemViewHolder {
        val items = ItemSuggestionSystemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuggestionSystemViewHolder(items)
    }

    override fun onBindViewHolder(holder: SuggestionSystemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}