package com.fastrata.eimprovement.features.approval.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalHistoryStatusModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.utils.DataDummySs
import javax.inject.Inject

class ListApprovalViewModel @Inject constructor(): ViewModel() {
    private val listApproval = MutableLiveData<ArrayList<ApprovalModel>>()
    fun setApproval() {
        // koneksi ke DB
        val data = DataDummySs.generateDummyApproval()

        listApproval.postValue(data)
    }
    fun getApproval(): LiveData<ArrayList<ApprovalModel>> {
        return listApproval
    }

    private val listApprovalHistoryStatus = MutableLiveData<ArrayList<ApprovalHistoryStatusModel>>()
    fun setApprovalHistoryStatus() {
        // koneksi ke DB
        val data = DataDummySs.generateDummyApprovalHistoryStatus()

        listApprovalHistoryStatus.postValue(data)
    }
    fun getApprovalHistoryStatus(): LiveData<ArrayList<ApprovalHistoryStatusModel>> {
        return listApprovalHistoryStatus
    }
}