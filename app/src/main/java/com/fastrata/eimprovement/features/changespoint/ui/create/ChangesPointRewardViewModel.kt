package com.fastrata.eimprovement.features.changespoint.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.RewardItem
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ChangesPointRewardViewModel @Inject constructor() : ViewModel() {
    private val list = MutableLiveData<ArrayList<RewardItem?>?>()

    fun setChangeRewardPoint() {
        val data = HawkUtils().getTempDataCreateCP()?.reward

        list.postValue(data)
    }

    fun getChangeRewardPoint(): LiveData<ArrayList<RewardItem?>?> {
        println("##### getChangePointRewardModel $list")
        return list
    }

    fun addReward(add: RewardItem,current:ArrayList<RewardItem?>?){
        current?.add(add)

        list.postValue(current)
        HawkUtils().setTempDataCreateCp(
            rewarddata = current
        )
    }

    fun updateReward(add: ArrayList<RewardItem?>?) {
        list.postValue(add)

        HawkUtils().setTempDataCreateCp(
            rewarddata = add
        )
    }

}