package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.CategorySuggestionItem
import com.fastrata.eimprovement.utils.DataDummySs
import javax.inject.Inject

class SsCreateCategorySuggestionViewModel @Inject constructor(): ViewModel() {
    private val list = MutableLiveData<ArrayList<CategorySuggestionItem?>?>()

    fun setCategorySuggestion() {
        // koneksi ke hawk
        val data = DataDummySs.generateDummyCategorySuggestion()

        println("### category suggestion : $data")

        list.postValue(data)
    }

    fun getCategorySuggestion(): LiveData<ArrayList<CategorySuggestionItem?>?> {
        return list
    }
}