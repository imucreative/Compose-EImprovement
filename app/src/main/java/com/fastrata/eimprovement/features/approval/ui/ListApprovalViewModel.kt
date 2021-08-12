package com.fastrata.eimprovement.features.approval.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.utils.DataDummySs

class ListApprovalViewModel: ViewModel() {
    private val listApproval = MutableLiveData<ArrayList<ApprovalModel>>()

    fun setApproval() {
        // koneksi ke DB
        val data = DataDummySs.generateDummyApproval()

        listApproval.postValue(data)
    }

    fun getApproval(): LiveData<ArrayList<ApprovalModel>> {
        println("##### getSuggestionSystem $listApproval")
        return listApproval
    }
}