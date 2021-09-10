package com.fastrata.eimprovement.features.changespoint.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRewardItem
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import javax.inject.Inject

class ChangesPointViewModel @Inject constructor() : ViewModel() {

    private val listSuggestionSystem = MutableLiveData<ArrayList<ChangePointModel>>()
    private val listchangereward = MutableLiveData<ArrayList<ChangePointRewardItem?>?>()

    fun setSuggestionSystem() {
        // koneksi ke DB
        val data = DataDummySs.generateDummyChangePointList()

        listSuggestionSystem.postValue(data)
    }

    fun getSuggestionSystem(): LiveData<ArrayList<ChangePointModel>> {
        println("##### getSuggestionSystem $listSuggestionSystem")
        return listSuggestionSystem
    }

    fun addReward(add : ChangePointRewardItem, curent : ArrayList<ChangePointRewardItem?>?){
        curent?.add(add)
        listchangereward.postValue(curent)

        HawkUtils().setTempDataCreateCp(
//            rewarddata = add
        )


    }
}

