package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemTeamMemberModel
import com.fastrata.eimprovement.utils.DataDummySs

class SuggestionSystemTeamMemberViewModel : ViewModel() {
    private val list = MutableLiveData<ArrayList<SuggestionSystemTeamMemberModel>>()

    fun setSuggestionSystemTeamMember() {
        // koneksi ke DB
        val data = DataDummySs.generateDummyTeamMember()

        list.postValue(data)
    }

    fun getSuggestionSystemTeamMember(): LiveData<ArrayList<SuggestionSystemTeamMemberModel>> {
        println("##### getSuggestionSystemTeamMember $list")
        return list
    }
}