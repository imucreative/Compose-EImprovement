package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem
import com.fastrata.eimprovement.utils.HawkUtils

class SsCreateViewModel : ViewModel() {
    private val list = MutableLiveData<ArrayList<TeamMemberItem?>?>()
    private val addData: ArrayList<TeamMemberItem?> = arrayListOf()

    fun setSuggestionSystemTeamMember() {
        // koneksi ke DB
        val data = HawkUtils().getTempDataCreateSs()?.teamMember

        list.postValue(data)
    }

    fun getSuggestionSystemTeamMember(): LiveData<ArrayList<TeamMemberItem?>?> {
        println("##### getSuggestionSystemTeamMember $list")
        return list
    }

    fun addTeamMember(add: TeamMemberItem) {
        val currentTeamMember = HawkUtils().getTempDataCreateSs()
        /*currentTeamMember?.teamMember?.let {
            addData.addAll(it)
        }*/
        addData.add(add)

        list.postValue(addData)

        HawkUtils().setTempDataCreateSs(
            teamMember = addData
        )
    }
}