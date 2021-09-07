package com.fastrata.eimprovement.features.changesPoint.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.changesPoint.data.model.ChangePointSystemModel
import com.fastrata.eimprovement.utils.DataDummySs
import javax.inject.Inject

class ChangesPointViewModel @Inject constructor() : ViewModel() {

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