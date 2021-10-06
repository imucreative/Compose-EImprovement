package com.fastrata.eimprovement.features.changespoint.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.RewardItem
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ChangesPointRewardViewModel @Inject constructor() : ViewModel() {
    private val list = MutableLiveData<ArrayList<RewardItem?>?>()
    private val totalReward = MutableLiveData<Int>()

    fun setChangeRewardPoint(source: String) {
        val data = HawkUtils().getTempDataCreateCP(source)?.reward

        list.postValue(data)
    }

    fun getChangeRewardPoint(): LiveData<ArrayList<RewardItem?>?> {
        return list
    }

    fun addReward(add: RewardItem,current:ArrayList<RewardItem?>?){
        current?.add(add)

        list.postValue(current)
        HawkUtils().setTempDataCreateCp(
            rewardData = current
        )
    }

    fun updateReward(add: ArrayList<RewardItem?>?) {
        list.postValue(add)

        HawkUtils().setTempDataCreateCp(
            rewardData = add
        )
    }

    fun getTotalReward():LiveData<Int>{
        return totalReward
    }

    fun setTotalReward(total : Int){
        totalReward.postValue(total)
    }

}