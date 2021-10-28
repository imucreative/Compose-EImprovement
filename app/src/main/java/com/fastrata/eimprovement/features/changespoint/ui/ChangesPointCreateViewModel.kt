package com.fastrata.eimprovement.features.changespoint.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.changespoint.data.CpRemoteRepository
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateItemModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRemoteRequest
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangesPointCreateViewModel @Inject constructor(private val repository: CpRemoteRepository) : ViewModel() {

    private val listChangePointDetail = MutableLiveData<ChangePointCreateItemModel>()

    fun setChangePointDetail(cpNo : String){
        println("# $cpNo")
        val data = DataDummySs.generateDummyDetailChangePoint()
        listChangePointDetail.postValue(data)
    }

    fun getChangePointDetail(): MutableLiveData<ChangePointCreateItemModel> {
        return listChangePointDetail
    }


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
    private val _detailCp = MutableLiveData<Event<LiveData<Result<ResultsResponse<ChangePointCreateItemModel>>>>>()
    val getDetailCpItem : LiveData<Event<LiveData<Result<ResultsResponse<ChangePointCreateItemModel>>>>> get() = _detailCp

    fun setDetailCp(id: Int, userId: Int){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default) { repository.observerDetailCp(id, userId) }
            _detailCp.value = Event(result)
        }
    }
}
