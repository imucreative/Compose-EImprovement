package com.fastrata.eimprovement.features.projectimprovement.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.utils.DataDummySs

class ProjectImprovementViewModel : ViewModel(){
    private val listProjectImprovement = MutableLiveData<ArrayList<ProjectImprovementModel>>()

    fun setProjectImprovement () {
        val data = DataDummySs.generateDummyProjectImprovementList()
        listProjectImprovement.postValue(data)
    }

    fun getProjectImprovement(): LiveData<ArrayList<ProjectImprovementModel>> {
        println("##### getProjectSystem $listProjectImprovement")
        return listProjectImprovement
    }




}