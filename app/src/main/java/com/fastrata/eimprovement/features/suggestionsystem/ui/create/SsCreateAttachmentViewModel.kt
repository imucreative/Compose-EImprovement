package com.fastrata.eimprovement.features.suggestionsystem.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber

class SsCreateAttachmentViewModel : ViewModel() {
    private val list = MutableLiveData<ArrayList<AttachmentItem?>?>()

    fun setSuggestionSystemAttachment() {
        // koneksi ke hawk
        val data = HawkUtils().getTempDataCreateSs()?.attachment
        Timber.d("### attachment : $data")

        list.postValue(data)
    }

    fun getSuggestionSystemAttachment(): LiveData<ArrayList<AttachmentItem?>?> {
        return list
    }

    fun addAttachment(add: AttachmentItem, current: ArrayList<AttachmentItem?>?) {
        current?.add(add)

        list.postValue(current)

        HawkUtils().setTempDataCreateSs(
            attachment = current
        )
    }

    fun updateAttachment(add: ArrayList<AttachmentItem?>?) {
        list.postValue(add)

        HawkUtils().setTempDataCreateSs(
            attachment = add
        )
    }
}