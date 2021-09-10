package com.fastrata.eimprovement.features.changespoint.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRewardItem
import com.fastrata.eimprovement.utils.DataDummySs
import javax.inject.Inject

class ChangesPointRewardViewModel @Inject constructor() : ViewModel() {
    private val list = MutableLiveData<ArrayList<ChangePointRewardItem>>()

    fun setChangeRewardPoint() {
        val data = DataDummySs.generaterewardmodel()

        list.postValue(data)
    }

    fun getChangeRewardPoint(): LiveData<ArrayList<ChangePointRewardItem>> {
        println("##### getChangePointRewardModel $list")
        return list
    }
}