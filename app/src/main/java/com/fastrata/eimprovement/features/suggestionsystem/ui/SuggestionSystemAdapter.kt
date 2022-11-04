package com.fastrata.eimprovement.features.suggestionsystem.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.R
import com.fastrata.eimprovement.databinding.ItemSuggestionSystemBinding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.utils.Tools

class SuggestionSystemAdapter : RecyclerView.Adapter<SuggestionSystemAdapter.SuggestionSystemViewHolder>() {

    private var list = ArrayList<SuggestionSystemModel>()

    fun setList(data: List<SuggestionSystemModel>?) {
        val convertToArrayList = Tools.listToArrayList(data)
        if (convertToArrayList != null) {
            list.addAll(convertToArrayList)
        }
        notifyDataSetChanged()
    }

    fun clear(){
        list.clear()
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
                createBy.text = data.createdBy
                date.text = data.date
                title.text = data.title
                status.text = data.status.status
                branch.text = data.branch
                subBranch.text = data.subBranch

                status.setTextColor(
                    when (data.status.id) {
                        5 -> ContextCompat.getColor(binding.root.context, R.color.blue_800)
                        4, 9 -> ContextCompat.getColor(binding.root.context, R.color.yellow_800)
                        10 -> ContextCompat.getColor(binding.root.context, R.color.green_800)
                        12 -> ContextCompat.getColor(binding.root.context, R.color.red_800)
                        else -> Color.BLACK
                    }
                )
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