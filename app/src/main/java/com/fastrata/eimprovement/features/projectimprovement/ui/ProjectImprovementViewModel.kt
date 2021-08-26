package com.fastrata.eimprovement.features.projectimprovement.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber

class ProjectImprovementViewModel : ViewModel(){
    private val listProjectImprovement = MutableLiveData<ArrayList<ProjectImprovementModel>>()
    private val listSebabMasalah = MutableLiveData<ArrayList<SebabMasalahModel>>()
    private val listAkarMasalah = MutableLiveData<ArrayList<AkarMasalahModel>>()
    private val listTeamMember = MutableLiveData<ArrayList<TeamMemberItem?>?>()

    fun setProjectImprovement () {
        val data = DataDummySs.generateDummyProjectImprovementList()
        listProjectImprovement.postValue(data)
    }

    fun getProjectImprovement(): LiveData<ArrayList<ProjectImprovementModel>> {
        println("##### getProjectSystem $listProjectImprovement")
        return listProjectImprovement
    }

    fun setSebabMasalah(){
        val data = DataDummySs.generateSebabMasalah()
        listSebabMasalah.postValue(data)
    }

    fun getSebabMasalah(): LiveData<ArrayList<SebabMasalahModel>>{
        println("#### getAkarMsalah $listSebabMasalah ")
        return listSebabMasalah
    }

    fun setAkarMasalah(){
        val data = DataDummySs.generateDummyAkarMasalah()
        listAkarMasalah.postValue(data)
    }

    fun getAkarMasalah() : LiveData<ArrayList<AkarMasalahModel>>{
        return listAkarMasalah
    }


    fun setSuggestionSystemTeamMember() {
        // koneksi ke hawk
        val data = HawkUtils().getTempDataCreateSs()?.teamMember
        Timber.d("### team member : $data")

        listTeamMember.postValue(data)
    }

    fun getSuggestionSystemTeamMember(): LiveData<ArrayList<TeamMemberItem?>?> {
        return listTeamMember
    }

    fun addTeamMember(add: TeamMemberItem, current: ArrayList<TeamMemberItem?>?) {
        current?.add(add)

        listTeamMember.postValue(current)

        HawkUtils().setTempDataCreateSs(
            teamMember = current
        )
    }

    fun updateTeamMember(add: ArrayList<TeamMemberItem?>?) {
        listTeamMember.postValue(add)

        HawkUtils().setTempDataCreateSs(
            teamMember = add
        )
    }





}