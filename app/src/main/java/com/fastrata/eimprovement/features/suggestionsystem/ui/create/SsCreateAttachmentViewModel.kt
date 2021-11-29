package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.featuresglobal.data.model.AttachmentItem
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class SsCreateAttachmentViewModel @Inject constructor(): ViewModel() {
    private val list = MutableLiveData<ArrayList<AttachmentItem?>?>()

    fun setSuggestionSystemAttachment(source: String) {
        // koneksi ke hawk
        val data = HawkUtils().getTempDataCreateSs(source)?.attachment
        Timber.d("### attachment : $data")

        list.postValue(data)
    }

    fun getSuggestionSystemAttachment(): LiveData<ArrayList<AttachmentItem?>?> {
        return list
    }

    fun addAttachment(add: AttachmentItem, current: ArrayList<AttachmentItem?>?, data: SuggestionSystemCreateModel?, source: String) {
        current?.add(add)

        list.postValue(current)

        HawkUtils().setTempDataCreateSs(
            id = data?.id,
            ssNo = data?.ssNo,
            date = data?.date,
            name = data?.name,
            nik = data?.nik,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            directMgr = data?.directMgr,
            title = data?.title,
            listCategory = data?.categoryImprovement,
            suggestion = data?.suggestion,
            problem = data?.problem,
            statusImplementation = data?.statusImplementation,
            teamMember = data?.teamMember,
            attachment = current,
            statusProposal = data?.statusProposal,
            source = source
        )
    }

    fun updateAttachment(add: ArrayList<AttachmentItem?>?, data: SuggestionSystemCreateModel?, source: String) {
        list.postValue(add)

        HawkUtils().setTempDataCreateSs(
            id = data?.id,
            ssNo = data?.ssNo,
            date = data?.date,
            name = data?.name,
            nik = data?.nik,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            directMgr = data?.directMgr,
            title = data?.title,
            listCategory = data?.categoryImprovement,
            suggestion = data?.suggestion,
            problem = data?.problem,
            statusImplementation = data?.statusImplementation,
            teamMember = data?.teamMember,
            attachment = add,
            statusProposal = data?.statusProposal,
            source = source
        )
    }
}