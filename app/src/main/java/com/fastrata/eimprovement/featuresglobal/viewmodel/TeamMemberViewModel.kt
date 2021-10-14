package com.fastrata.eimprovement.featuresglobal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.data.GlobalRemoteRepository
import com.fastrata.eimprovement.featuresglobal.data.model.MemberDepartmentItem
import com.fastrata.eimprovement.featuresglobal.data.model.MemberNameItem
import com.fastrata.eimprovement.featuresglobal.data.model.MemberTaskItem
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeamMemberViewModel @Inject constructor(private val repository: GlobalRemoteRepository): ViewModel() {
    // === Team Member Name
    private val _listTeamMemberName = MutableLiveData<Event<LiveData<Result<ResultsResponse<MemberNameItem>>>>>()
    val getTeamMemberName: LiveData<Event<LiveData<Result<ResultsResponse<MemberNameItem>>>>> get() = _listTeamMemberName

    fun setTeamMemberName(branchCode: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListTeamMember(branchCode) }
            _listTeamMemberName.value = Event(result)
        }
    }

    // === Department
    private val _listDepartment = MutableLiveData<Event<LiveData<Result<ResultsResponse<MemberDepartmentItem>>>>>()
    val getDepartment: LiveData<Event<LiveData<Result<ResultsResponse<MemberDepartmentItem>>>>> get() = _listDepartment

    fun setDepartment() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListDepartment() }
            _listDepartment.value = Event(result)
        }
    }

    // === Team Role/ Task
    private val _listTeamRole = MutableLiveData<Event<LiveData<Result<ResultsResponse<MemberTaskItem>>>>>()
    val getTeamRole: LiveData<Event<LiveData<Result<ResultsResponse<MemberTaskItem>>>>> get() = _listTeamRole

    fun setTeamRole() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListTeamRole() }
            _listTeamRole.value = Event(result)
        }
    }
}