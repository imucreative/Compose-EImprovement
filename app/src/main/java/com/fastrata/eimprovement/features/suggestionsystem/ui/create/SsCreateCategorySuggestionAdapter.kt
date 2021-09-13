package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fastrata.eimprovement.databinding.ItemCategorySuggestionBinding
import com.fastrata.eimprovement.features.suggestionsystem.data.model.CategorySuggestionItem
import kotlin.collections.ArrayList

class SsCreateCategorySuggestionAdapter: RecyclerView.Adapter<SsCreateCategorySuggestionAdapter.CategoryViewHolder>() {
    private var list = ArrayList<CategorySuggestionItem?>()
    fun setListCategorySuggestion(data: ArrayList<CategorySuggestionItem?>, checked: ArrayList<CategorySuggestionItem?>?) {
        list.clear()

        val mergedList: ArrayList<CategorySuggestionItem?> = arrayListOf()
        mergedList.addAll(data)

        checked?.map {
            it?.let { value ->
                if (value.id != 0) {
                    mergedList.remove(
                        CategorySuggestionItem(
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

    private lateinit var callback: SuggestionSystemCreateCategorySuggestionCallback
    fun ssCreateCallback(callback: SuggestionSystemCreateCategorySuggestionCallback) {
        this.callback = callback
    }

    inner class CategoryViewHolder(private val binding: ItemCategorySuggestionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategorySuggestionItem) {
            binding.apply {
                tvCategorySuggestion.text = data.category
                chbxCategorySuggestion.isChecked = data.checked

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
