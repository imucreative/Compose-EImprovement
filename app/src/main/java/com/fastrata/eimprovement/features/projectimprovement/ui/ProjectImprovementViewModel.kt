package com.fastrata.eimprovement.features.projectimprovement.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.CategorySuggestionItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber

class ProjectImprovementViewModel : ViewModel(){
    private val listProjectImprovement = MutableLiveData<ArrayList<ProjectImprovementItem>>()
    private val listSebabMasalah = MutableLiveData<ArrayList<SebabMasalahItem>>()
    private val listAkarMasalah = MutableLiveData<ArrayList<AkarMasalahItem>>()
    private val listTeamMember = MutableLiveData<ArrayList<TeamMemberItem?>?>()
    private val listAttachment = MutableLiveData<ArrayList<AttachmentItem?>?>()
    private val listCategory = MutableLiveData<ArrayList<CategorySuggestionItem?>?>()

    fun setProjectImprovement () {
        val data = DataDummySs.generateDummyProjectImprovementList()
        listProjectImprovement.postValue(data)
    }

    fun getProjectImprovement(): LiveData<ArrayList<ProjectImprovementItem>> {
        println("##### getProjectSystem $listProjectImprovement")
        return listProjectImprovement
    }

    fun setSebabMasalah(){
        val data = DataDummySs.generateSebabMasalah()
        listSebabMasalah.postValue(data)
    }

    fun getSebabMasalah(): LiveData<ArrayList<SebabMasalahItem>>{
        println("#### getAkarMsalah $listSebabMasalah ")
        return listSebabMasalah
    }

    fun setAkarMasalah(){
        val data = DataDummySs.generateDummyAkarMasalah()
        listAkarMasalah.postValue(data)
    }

    fun getAkarMasalah() : LiveData<ArrayList<AkarMasalahItem>>{
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

    fun setpiattachment(){
        val data = HawkUtils().getTempDataCreateSs()?.attachment
        Timber.d("### attachment : $data")

        listAttachment.postValue(data)
    }

    fun getpiattachment():LiveData<ArrayList<AttachmentItem?>?>{
        return listAttachment
    }

    fun addAttachment(add: AttachmentItem, current: ArrayList<AttachmentItem?>?) {
        current?.add(add)

        listAttachment.postValue(current)

        HawkUtils().setTempDataCreateSs(
            attachment = current
        )
    }

    fun updateAttachment(add: ArrayList<AttachmentItem?>?) {
        listAttachment.postValue(add)

        HawkUtils().setTempDataCreateSs(
            attachment = add
        )
    }

    fun setCategorySuggestion() {
        // koneksi ke hawk
        val data = DataDummySs.generateDummyCategorySuggestion()

        println("### category suggestion : $data")

        listCategory.postValue(data)
    }

    fun getCategorySuggestion(): LiveData<ArrayList<CategorySuggestionItem?>?> {
        return listCategory
    }




}