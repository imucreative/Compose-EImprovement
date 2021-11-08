package com.fastrata.eimprovement.featuresglobal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.data.GlobalRemoteRepository
import com.fastrata.eimprovement.featuresglobal.data.model.AttachmentItem
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class AttachmentViewModel @Inject constructor(private val repository: GlobalRemoteRepository): ViewModel() {

    lateinit var file: MultipartBody.Part
    lateinit var type: String
    lateinit var group: String
    lateinit var createdBy: String

    // === submit attachment
    private val _doSubmitAttachment = MutableLiveData<Event<LiveData<Result<ResultsResponse<AttachmentItem>>>>>()
    val doSubmitAttachment: LiveData<Event<LiveData<Result<ResultsResponse<AttachmentItem>>>>> get() = _doSubmitAttachment

    fun processSubmitAttachment() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeUploadAttachment(file, type, group, createdBy) }
            _doSubmitAttachment.value = Event(result)
        }
    }

    // === remove attachment
    private val _doRemoveAttachment = MutableLiveData<Event<LiveData<Result<ResultsResponse<String>>>>>()
    val doRemoveAttachment: LiveData<Event<LiveData<Result<ResultsResponse<String>>>>> get() = _doRemoveAttachment

    fun processRemoveAttachment(attachmentId: Int, fileName: String, typeAttachment: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeRemoveAttachment(attachmentId, fileName, typeAttachment) }
            _doRemoveAttachment.value = Event(result)
        }
    }
}