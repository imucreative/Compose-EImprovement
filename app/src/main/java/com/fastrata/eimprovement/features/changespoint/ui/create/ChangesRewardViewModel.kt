package com.fastrata.eimprovement.features.changespoint.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.features.changespoint.data.CpRemoteRepository
import com.fastrata.eimprovement.features.changespoint.data.model.GiftItem
import com.fastrata.eimprovement.features.changespoint.data.model.RewardItem
import com.fastrata.eimprovement.featuresglobal.data.GlobalRemoteRepository
import com.fastrata.eimprovement.featuresglobal.data.model.MemberDepartmentItem
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangesRewardViewModel @Inject constructor(private val repository: CpRemoteRepository) : ViewModel() {
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

    // === Update to remove gift
    fun updateReward(add: ArrayList<RewardItem?>?) {
        list.postValue(add)

        HawkUtils().setTempDataCreateCp(
            rewardData = add
        )
    }

    // === Get Calculate Total Reward
    fun getTotalReward():LiveData<Int>{
        return totalReward
    }

    fun setTotalReward(total : Int){
        totalReward.postValue(total)
    }

    // === Get All Gift
    private val _listGift = MutableLiveData<Event<LiveData<Result<ResultsResponse<GiftItem>>>>>()
    val getAllGift: LiveData<Event<LiveData<Result<ResultsResponse<GiftItem>>>>> get() = _listGift

    fun setAllGift() {
        viewModelScope.launch {
            val result = repository.observeListGift()
            _listGift.value = Event(result)
        }
    }

}