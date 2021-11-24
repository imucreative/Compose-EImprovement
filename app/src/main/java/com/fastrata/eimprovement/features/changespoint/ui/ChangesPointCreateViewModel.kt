package com.fastrata.eimprovement.features.changespoint.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.changespoint.data.CpRemoteRepository
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRemoteRequest
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointResponseModel
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangesPointCreateViewModel @Inject constructor(private val repository: CpRemoteRepository) : ViewModel() {

    // === List CP
    private val _listCp = MutableLiveData<Event<LiveData<Result<ResultsResponse<ChangePointModel>>>>>()
    val getListCpItem : LiveData<Event<LiveData<Result<ResultsResponse<ChangePointModel>>>>> get() = _listCp

    fun setListCp(listChangePointRemoteRequest: ChangePointRemoteRequest){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default) { repository.observerListCp(listChangePointRemoteRequest) }
            _listCp.value = Event(result)
        }
    }

    // === Detail CP
    private val _detailCp = MutableLiveData<Event<LiveData<Result<ResultsResponse<ChangePointCreateModel>>>>>()
    val getDetailCp : LiveData<Event<LiveData<Result<ResultsResponse<ChangePointCreateModel>>>>> get() = _detailCp

    fun setDetailCp(id: Int, userId: Int){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default) { repository.observerDetailCp(id, userId) }
            _detailCp.value = Event(result)
        }
    }

    // === post Submit Create Cp
    private val _postSubmitCreateCp = MutableLiveData<Event<LiveData<Result<ResultsResponse<ChangePointResponseModel>>>>> ()
    val postSubmitCreateCp: LiveData<Event<LiveData<Result<ResultsResponse<ChangePointResponseModel>>>>> get() = _postSubmitCreateCp

    fun setPostSubmitCreateCp(changePointCreateModel: ChangePointCreateModel) {
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default){ repository.observeSubmitCreateCp(changePointCreateModel)}
            _postSubmitCreateCp.value = Event(result)
        }
    }

    // === Put Submit Updaten Cp
    private val _putSubmitUpdateCp = MutableLiveData<Event<LiveData<Result<ResultsResponse<ChangePointResponseModel>>>>>()
    val putSubmitUpdateCp : LiveData<Event<LiveData<Result<ResultsResponse<ChangePointResponseModel>>>>> get() = _putSubmitUpdateCp

    fun setPutSubmitUpdateCp(changePointCreateModel: ChangePointCreateModel){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default){ repository.observeSubmitUpdateCp(changePointCreateModel)}
            _putSubmitUpdateCp.value = Event(result)
        }
    }

    //=== Remove List CP
    private val _removeListCp = MutableLiveData<Event<LiveData<Result<ResultsResponse<ArrayList<String>>>>>>()
    val doRemoveCp: LiveData<Event<LiveData<Result<ResultsResponse<ArrayList<String>>>>>> get() =  _removeListCp

    fun deleteCpList(idCp : Int){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default){ repository.observeRemoveCp(idCp)}
            _removeListCp.value = Event(result)
        }
    }

}
