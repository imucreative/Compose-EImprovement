package com.fastrata.eimprovement.features.projectimprovement.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.ui.model.*
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.Tools
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovementViewModel @Inject constructor(): ViewModel(){
    private val listProjectImprovement = MutableLiveData<ArrayList<ProjectImprovementModel>>()
    private val detailProjectImprovement = MutableLiveData<ProjectImprovementCreateModel>()
    private val listSebabMasalah = MutableLiveData<ArrayList<SebabMasalahItem?>?>()
    private val listAkarMasalah = MutableLiveData<ArrayList<AkarMasalahItem?>?>()
    private val listTeamMember = MutableLiveData<ArrayList<TeamMemberItem?>?>()
    private val listAttachment = MutableLiveData<ArrayList<AttachmentItem?>?>()
    private val listCategory = MutableLiveData<ArrayList<CategoryImprovementItem?>?>()

    fun setProjectImprovement () {
        val data = DataDummySs.generateDummyProjectImprovementList()
        listProjectImprovement.postValue(data)
    }

    fun getProjectImprovement(): LiveData<ArrayList<ProjectImprovementModel>> {
        return listProjectImprovement
    }

    fun setProjectImprovementDetail(piNo: String) {
        // koneksi ke DB
        println("# $piNo")
        val data = DataDummySs.generateDummyDetailProjectImprovementList()
        detailProjectImprovement.postValue(data)
    }

    fun getProjectImprovementDetail(): LiveData<ProjectImprovementCreateModel> {
        return detailProjectImprovement
    }

    fun setSebabMasalah(source: String){
        val data = HawkUtils().getTempDataCreatePi(source)?.sebabMasalah
        listSebabMasalah.postValue(data)
    }

    fun getSebabMasalah(): LiveData<ArrayList<SebabMasalahItem?>?>{
        return listSebabMasalah
    }

    fun addSebabMasalah(add: SebabMasalahItem, current: ArrayList<SebabMasalahItem?>?) {
        current?.add(add)

        listSebabMasalah.postValue(current)

        HawkUtils().setTempDataCreatePi(
            sebabMasalah = current
        )
    }

    fun updateSebabMasalah(add: ArrayList<SebabMasalahItem?>?) {
        listSebabMasalah.postValue(add)

        HawkUtils().setTempDataCreatePi(
            sebabMasalah = add
        )
    }

    fun setAkarMasalah(source: String){
        val data = HawkUtils().getTempDataCreatePi(source)?.akarMasalah

        listAkarMasalah.postValue(data)
    }

    fun getAkarMasalah() : LiveData<ArrayList<AkarMasalahItem?>?>{
        return listAkarMasalah
    }

    fun updateAkarMasalah(add: AkarMasalahItem, index: Int, source: String) {
        val data = HawkUtils().getTempDataCreatePi(source)?.akarMasalah

        // != -1 = add
        if (index != -1) {
            data?.removeAt(index)
        }
        data?.add(add)

        val sortMergedList = data?.sortedBy { it?.sequence }
        val convertToArrayList = Tools.listToArrayList(sortMergedList)

        HawkUtils().setTempDataCreatePi(
            akarMasalah = convertToArrayList
        )
    }

    fun removeAkarMasalah(index: Int, current: ArrayList<AkarMasalahItem?>?) {
        current?.removeAt(index)

        HawkUtils().setTempDataCreatePi(
            akarMasalah = current
        )
    }

    fun setSuggestionSystemTeamMember(source: String) {
        // koneksi ke hawk
        val data = HawkUtils().getTempDataCreatePi(source)?.teamMember
        Timber.d("### team member : $data")

        listTeamMember.postValue(data)
    }

    fun getSuggestionSystemTeamMember(): LiveData<ArrayList<TeamMemberItem?>?> {
        return listTeamMember
    }

    fun addTeamMember(add: TeamMemberItem, current: ArrayList<TeamMemberItem?>?) {
        current?.add(add)

        listTeamMember.postValue(current)

        HawkUtils().setTempDataCreatePi(
            teamMember = current
        )
    }

    fun updateTeamMember(add: ArrayList<TeamMemberItem?>?) {
        listTeamMember.postValue(add)

        HawkUtils().setTempDataCreatePi(
            teamMember = add
        )
    }

    fun setAttachment(source: String){
        val data = HawkUtils().getTempDataCreatePi(source)?.attachment
        Timber.d("### attachment : $data")

        listAttachment.postValue(data)
    }

    fun getAttachment():LiveData<ArrayList<AttachmentItem?>?>{
        return listAttachment
    }

    fun addAttachment(add: AttachmentItem, current: ArrayList<AttachmentItem?>?) {
        current?.add(add)

        listAttachment.postValue(current)

        HawkUtils().setTempDataCreatePi(
            attachment = current
        )
    }

    fun updateAttachment(add: ArrayList<AttachmentItem?>?) {
        listAttachment.postValue(add)

        HawkUtils().setTempDataCreatePi(
            attachment = add
        )
    }

    fun setCategorySuggestion() {
        // koneksi ke hawk
        val data = DataDummySs.generateDummyCategorySuggestion()

        println("### category suggestion : $data")

        listCategory.postValue(data)
    }

    fun getCategorySuggestion(): LiveData<ArrayList<CategoryImprovementItem?>?> {
        return listCategory
    }

}