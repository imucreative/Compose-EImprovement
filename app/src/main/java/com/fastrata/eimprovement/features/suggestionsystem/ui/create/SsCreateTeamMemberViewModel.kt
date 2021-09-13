package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class SsCreateTeamMemberViewModel @Inject constructor(): ViewModel() {
    private val list = MutableLiveData<ArrayList<TeamMemberItem?>?>()

    fun setSuggestionSystemTeamMember(source: String) {
        // koneksi ke hawk
        val data = HawkUtils().getTempDataCreateSs(source)?.teamMember
        Timber.d("### team member : $data")

        list.postValue(data)
    }

    fun getSuggestionSystemTeamMember(): LiveData<ArrayList<TeamMemberItem?>?> {
        return list
    }

    fun addTeamMember(add: TeamMemberItem, current: ArrayList<TeamMemberItem?>?) {
        current?.add(add)

        list.postValue(current)

        HawkUtils().setTempDataCreateSs(
            teamMember = current
        )
    }

    fun updateTeamMember(add: ArrayList<TeamMemberItem?>?) {
        list.postValue(add)

        HawkUtils().setTempDataCreateSs(
            teamMember = add
        )
    }
}