package com.fastrata.eimprovement.features.changesPoint.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.changesPoint.data.model.ChangePointRewardModel
import com.fastrata.eimprovement.utils.DataDummySs
import javax.inject.Inject

class ChangesPointRewardViewModel @Inject constructor() : ViewModel() {
    private val list = MutableLiveData<ArrayList<ChangePointRewardModel>>()

    fun setChangeRewardPoint() {
        val data = DataDummySs.generaterewardmodel()

        list.postValue(data)
    }

    fun getChangeRewardPoint(): LiveData<ArrayList<ChangePointRewardModel>> {
        println("##### getChangePointRewardModel $list")
        return list
    }
}