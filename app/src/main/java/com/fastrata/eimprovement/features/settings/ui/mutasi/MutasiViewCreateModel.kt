package com.fastrata.eimprovement.features.settings.ui.mutasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.settings.ui.mutasi.data.model.MutasiModel
import com.fastrata.eimprovement.utils.DataDummySs
import javax.inject.Inject

class MutasiViewCreateModel @Inject constructor() : ViewModel(){
    private val listMutasi = MutableLiveData<ArrayList<MutasiModel>>()

    fun setMutasi(){
        val data = DataDummySs.generateMutasiDummy()

        listMutasi.postValue(data)
    }

    fun getMutasi(): LiveData<ArrayList<MutasiModel>>{
        println("##### getmutasi $listMutasi")
        return listMutasi
    }
}