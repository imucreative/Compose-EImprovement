package com.fastrata.eimprovement.features.settings.ui.mutasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.settings.ui.mutasi.data.model.MutasiModel
import com.fastrata.eimprovement.features.settings.ui.mutasi.data.model.MutasiRemoteRepository
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MutasiViewCreateModel @Inject constructor(private val repository : MutasiRemoteRepository) : ViewModel(){
    private val _listMutasi = MutableLiveData<Event<LiveData<Result<ResultsResponse<MutasiModel>>>>>()
    val getListMutasi : LiveData<Event<LiveData<Result<ResultsResponse<MutasiModel>>>>> get() = _listMutasi

    fun setMutasi(
        userId : Int,
        limit : Int,
        page: Int
    ){
      viewModelScope.launch(Dispatchers.Main){
          val result = withContext(Dispatchers.Default) { repository.observeListMutasi(userId,limit,page)}
          _listMutasi.value = Event(result)
      }
    }

//    fun getMutasi(): LiveData<ArrayList<MutasiModel>>{
//        println("##### getmutasi $listMutasi")
//        return listMutasi
//    }
}