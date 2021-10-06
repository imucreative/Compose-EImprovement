package com.fastrata.eimprovement.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemCategorySuggestionBinding
import com.fastrata.eimprovement.ui.model.CategoryImprovementItem
import com.fastrata.eimprovement.utils.APPROVE

class CategoryImprovementAdapter: RecyclerView.Adapter<CategoryImprovementAdapter.CategoryViewHolder>() {
    private var list = ArrayList<CategoryImprovementItem?>()
    private var action = ""
    fun setListCategoryImprovement(data: ArrayList<CategoryImprovementItem?>, checked: ArrayList<CategoryImprovementItem?>?, act: String) {
        list.clear()
        action = act
        val mergedList: ArrayList<CategoryImprovementItem?> = arrayListOf()
        mergedList.addAll(data)

        checked?.map {
            it?.let { value ->
                if (value.id != 0) {
                    mergedList.remove(
                        CategoryImprovementItem(
                            id = value.id,
                            category = value.category,
                            checked = false
                        )
                    )
                    mergedList.add(value)
                }
            }
        }

        if (!checked.isNullOrEmpty()){
            val sortMergedList = mergedList.sortedBy { it?.id }
            list.addAll(sortMergedList)
        } else {
            list.addAll(data)
        }

        notifyDataSetChanged()
    }

    private lateinit var callback: CategoryImprovementCallback
    fun categoryImprovementCreateCallback(callback: CategoryImprovementCallback) {
        this.callback = callback
    }

    inner class CategoryViewHolder(private val binding: ItemCategorySuggestionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategoryImprovementItem) {
            binding.apply {
                tvCategorySuggestion.text = data.category
                chbxCategorySuggestion.isChecked = data.checked

                if (action == APPROVE) {
                    chbxCategorySuggestion.isEnabled = false
                    tvCategorySuggestion.isClickable = false
                } else {
                    tvCategorySuggestion.setOnClickListener {
                        chbxCategorySuggestion.isChecked = !chbxCategorySuggestion.isChecked

                        if (chbxCategorySuggestion.isChecked) {
                            callback.checkClicked(data, true)
                        } else if (!chbxCategorySuggestion.isChecked) {
                            callback.checkClicked(data, false)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val items = ItemCategorySuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(items)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}