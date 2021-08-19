package com.fastrata.eimprovement.features.ChangesPoint.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.ChangesPoint.data.model.ChangePointSystemModel
import com.fastrata.eimprovement.utils.DataDummySs

class ChangesPointViewModel : ViewModel() {

    private val listSuggestionSystem = MutableLiveData<ArrayList<ChangePointSystemModel>>()

    fun setSuggestionSystem() {
        // koneksi ke DB
        val data = DataDummySs.generateDummyChangePointList()

        listSuggestionSystem.postValue(data)
    }

    fun getSuggestionSystem(): LiveData<ArrayList<ChangePointSystemModel>> {
        println("##### getSuggestionSystem $listSuggestionSystem")
        return listSuggestionSystem
    }
}