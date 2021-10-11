package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.suggestionsystem.data.SsRemoteRepository
import com.fastrata.eimprovement.ui.model.MemberDepartmentItem
import com.fastrata.eimprovement.ui.model.MemberNameItem
import com.fastrata.eimprovement.ui.model.MemberTaskItem
import com.fastrata.eimprovement.ui.model.TeamMemberItem
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SsCreateTeamMemberViewModel @Inject constructor(private val repository: SsRemoteRepository): ViewModel() {
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

    fun addTeamMember(add: TeamMemberItem, current: ArrayList<TeamMemberItem?>?, source: String) {
        current?.add(add)

        list.postValue(current)

        HawkUtils().setTempDataCreateSs(
            teamMember = current,
            source = source
        )
    }

    fun updateTeamMember(add: ArrayList<TeamMemberItem?>?, source: String) {
        list.postValue(add)

        HawkUtils().setTempDataCreateSs(
            teamMember = add,
            source = source
        )
    }

    // === Team Member Name
    private val _listTeamMemberName = MutableLiveData<Event<LiveData<Result<ResultsResponse<MemberNameItem>>>>>()
    val getTeamMemberName: LiveData<Event<LiveData<Result<ResultsResponse<MemberNameItem>>>>> get() = _listTeamMemberName

    fun setTeamMemberName(branchCode: String) {
        viewModelScope.launch {
            val result = repository.observeListTeamMember(branchCode)
            _listTeamMemberName.value = Event(result)
        }
    }

    // === Department
    private val _listDepartment = MutableLiveData<Event<LiveData<Result<ResultsResponse<MemberDepartmentItem>>>>>()
    val getDepartment: LiveData<Event<LiveData<Result<ResultsResponse<MemberDepartmentItem>>>>> get() = _listDepartment

    fun setDepartment() {
        viewModelScope.launch {
            val result = repository.observeListDepartment()
            _listDepartment.value = Event(result)
        }
    }

    // === Team Role/ Task
    private val _listTeamRole = MutableLiveData<Event<LiveData<Result<ResultsResponse<MemberTaskItem>>>>>()
    val getTeamRole: LiveData<Event<LiveData<Result<ResultsResponse<MemberTaskItem>>>>> get() = _listTeamRole

    fun setTeamRole() {
        viewModelScope.launch {
            val result = repository.observeListTeamRole()
            _listTeamRole.value = Event(result)
        }
    }
}