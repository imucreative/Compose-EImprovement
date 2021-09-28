package com.fastrata.eimprovement.features.projectimprovement.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.ui.model.*
import com.fastrata.eimprovement.utils.DataDummySs
import com.fastrata.eimprovement.utils.HawkUtils
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovementViewModel @Inject constructor(): ViewModel(){
    private val listProjectImprovement = MutableLiveData<ArrayList<ProjectImprovementModel>>()
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

    fun setSebabMasalah(){
        val data = HawkUtils().getTempDataCreatePi()?.problem
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

    fun displayAkarMasalah(dataSaranAkarMasalah: ArrayList<AkarMasalahItem?>){
        listAkarMasalah.postValue(dataSaranAkarMasalah)

        HawkUtils().setTempDataCreatePi(
            akarMasalah = dataSaranAkarMasalah
        )
    }

    fun setAkarMasalah(){
        val data = HawkUtils().getTempDataCreatePi()?.akarMasalah

        listAkarMasalah.postValue(data)
    }

    fun getAkarMasalah() : LiveData<ArrayList<AkarMasalahItem?>?>{
        return listAkarMasalah
    }

    fun updateAkarMasalah(add: AkarMasalahItem, index: Int) {
        val data = HawkUtils().getTempDataCreatePi()?.akarMasalah

        // != -1 = add
        if (index != -1) {
            data?.removeAt(index)
        }
        data?.add(add)

        val sortMergedList = data?.sortedBy { it?.sequence }
        val convertToArrayList = listToArrayList(sortMergedList)

        HawkUtils().setTempDataCreatePi(
            akarMasalah = convertToArrayList
        )
    }

    private fun <T> listToArrayList(list: List<T>?): ArrayList<T>? {
        return list?.let { ArrayList(it) }
    }

    fun removeAkarMasalah(index: Int, current: ArrayList<AkarMasalahItem?>?) {
        current?.removeAt(index)

        HawkUtils().setTempDataCreatePi(
            akarMasalah = current
        )
    }

    fun setSuggestionSystemTeamMember() {
        // koneksi ke hawk
        val data = HawkUtils().getTempDataCreatePi()?.teamMember
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

    fun setAttachment(){
        val data = HawkUtils().getTempDataCreatePi()?.attachment
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