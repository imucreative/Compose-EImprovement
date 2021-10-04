package com.fastrata.eimprovement.features.changespoint.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateItemModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.utils.DataDummySs
import javax.inject.Inject

class ChangesPointCreateViewModel @Inject constructor() : ViewModel() {

    private val listChangePoint = MutableLiveData<ArrayList<ChangePointModel>>()

    private val listChangePointDetail = MutableLiveData<ChangePointCreateItemModel>()

    fun setChangePoint() {
        val data = DataDummySs.generateDummyChangePointList()

        listChangePoint.postValue(data)
    }

    fun getChangePoint(): LiveData<ArrayList<ChangePointModel>> {
        println("##### getSuggestionSystem $listChangePoint")
        return listChangePoint
    }

    fun setChangePointDetail(cpNo : String){
        println("# $cpNo")
        val data = DataDummySs.generateDummyDetailChangePoint()
        listChangePointDetail.postValue(data)
    }

    fun getChangePointDetail(): MutableLiveData<ChangePointCreateItemModel> {
        return listChangePointDetail
    }
}

