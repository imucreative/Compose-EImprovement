package com.fastrata.eimprovement.features.suggestionsystem.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.utils.DataDummySs
import javax.inject.Inject

class SuggestionSystemViewModel @Inject constructor(): ViewModel() {
    private val listSuggestionSystem = MutableLiveData<ArrayList<SuggestionSystemModel>>()

    fun setSuggestionSystem() {
        // koneksi ke DB
        val data = DataDummySs.generateDummySuggestionSystem()

        listSuggestionSystem.postValue(data)
    }

    fun getSuggestionSystem(): LiveData<ArrayList<SuggestionSystemModel>> {
        println("##### getSuggestionSystem $listSuggestionSystem")
        return listSuggestionSystem
    }
}