package com.fastrata.eimprovement.features.projectimprovement.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fastrata.eimprovement.api.ResultsResponse
import com.fastrata.eimprovement.data.Result
import com.fastrata.eimprovement.featuresglobal.data.model.AttachmentItem
import com.fastrata.eimprovement.featuresglobal.data.model.TeamMemberItem
import com.fastrata.eimprovement.features.projectimprovement.data.PiRemoteRepository
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.utils.HawkUtils
import com.fastrata.eimprovement.utils.Tools
import com.fastrata.eimprovement.wrapper.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ProjectImprovementViewModel @Inject constructor(private val repository: PiRemoteRepository): ViewModel(){
    private val listSebabMasalah = MutableLiveData<ArrayList<SebabMasalahModel?>?>()
    private val listAkarMasalah = MutableLiveData<ArrayList<AkarMasalahModel?>?>()
    private val listTeamMember = MutableLiveData<ArrayList<TeamMemberItem?>?>()
    private val listAttachment = MutableLiveData<ArrayList<AttachmentItem?>?>()

    // Sebab Masalah
    fun setSebabMasalah(source: String){
        val data = HawkUtils().getTempDataCreatePi(source)?.sebabMasalah
        listSebabMasalah.postValue(data)
    }

    fun getSebabMasalah(): LiveData<ArrayList<SebabMasalahModel?>?>{
        return listSebabMasalah
    }

    fun addSebabMasalah(add: SebabMasalahModel, current: ArrayList<SebabMasalahModel?>?, source: String) {
        current?.add(add)

        listSebabMasalah.postValue(current)

        val data = HawkUtils().getTempDataCreatePi(source)

        HawkUtils().setTempDataCreatePi(
            id = data?.id,
            piNo = data?.piNo,
            date = data?.date,
            title = data?.title,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            years = data?.years,
            statusImplementationModel = data?.statusImplementationModel,
            identification = data?.identification,
            target = data?.target,
            sebabMasalah = current,
            akarMasalah = data?.akarMasalah,
            nilaiOutput = data?.nilaiOutput,
            nqiModel = data?.nqiModel,
            teamMember = data?.teamMember,
            categoryFixing = data?.categoryFixing,
            hasilImplementasi = data?.implementationResult,
            attachment = data?.attachment,
            statusProposal = data?.statusProposal,
            source = source
        )
    }

    fun updateSebabMasalah(add: ArrayList<SebabMasalahModel?>?, source: String) {
        listSebabMasalah.postValue(add)

        val data = HawkUtils().getTempDataCreatePi(source)

        HawkUtils().setTempDataCreatePi(
            id = data?.id,
            piNo = data?.piNo,
            date = data?.date,
            title = data?.title,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            years = data?.years,
            statusImplementationModel = data?.statusImplementationModel,
            identification = data?.identification,
            target = data?.target,
            sebabMasalah = add,
            akarMasalah = data?.akarMasalah,
            nilaiOutput = data?.nilaiOutput,
            nqiModel = data?.nqiModel,
            teamMember = data?.teamMember,
            categoryFixing = data?.categoryFixing,
            hasilImplementasi = data?.implementationResult,
            attachment = data?.attachment,
            statusProposal = data?.statusProposal,
            source = source
        )
    }

    // Akar Masalah
    fun setAkarMasalah(source: String){
        val data = HawkUtils().getTempDataCreatePi(source)?.akarMasalah

        listAkarMasalah.postValue(data)
    }

    fun getAkarMasalah() : LiveData<ArrayList<AkarMasalahModel?>?>{
        return listAkarMasalah
    }

    fun updateAkarMasalah(add: AkarMasalahModel, index: Int, source: String): ArrayList<AkarMasalahModel?>? {
        val data = HawkUtils().getTempDataCreatePi(source)

        // != -1 = add
        if (index != -1) {
            data?.akarMasalah?.removeAt(index)
        }
        data?.akarMasalah?.add(add)

        val sortMergedList = data?.akarMasalah?.sortedBy { it?.sequence }
        val convertToArrayList = Tools.listToArrayList(sortMergedList)

        HawkUtils().setTempDataCreatePi(
            id = data?.id,
            piNo = data?.piNo,
            date = data?.date,
            title = data?.title,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            years = data?.years,
            statusImplementationModel = data?.statusImplementationModel,
            identification = data?.identification,
            target = data?.target,
            sebabMasalah = data?.sebabMasalah,
            akarMasalah = convertToArrayList,
            nilaiOutput = data?.nilaiOutput,
            nqiModel = data?.nqiModel,
            teamMember = data?.teamMember,
            categoryFixing = data?.categoryFixing,
            hasilImplementasi = data?.implementationResult,
            attachment = data?.attachment,
            statusProposal = data?.statusProposal,
            headId = data?.headId,
            userId = data?.userId,
            orgId = data?.orgId,
            warehouseId = data?.warehouseId,
            historyApproval = data?.historyApproval,
            activityType = data?.activityType,
            submitType = data?.submitType,
            comment = data?.comment,
            source = source
        )

        return convertToArrayList
    }

    fun removeAkarMasalah(index: Int, current: ArrayList<AkarMasalahModel?>?, source: String) {
        current?.removeAt(index)

        HawkUtils().setTempDataCreatePi(
            akarMasalah = current,
            source = source
        )
    }

    // Team Member
    fun setSuggestionSystemTeamMember(source: String) {
        // koneksi ke hawk
        val data = HawkUtils().getTempDataCreatePi(source)?.teamMember
        Timber.d("### team member : $data")

        listTeamMember.postValue(data)
    }

    fun getSuggestionSystemTeamMember(): LiveData<ArrayList<TeamMemberItem?>?> {
        return listTeamMember
    }

    fun addTeamMember(add: TeamMemberItem, current: ArrayList<TeamMemberItem?>?, source: String) {
        current?.add(add)

        listTeamMember.postValue(current)

        val data = HawkUtils().getTempDataCreatePi(source)

        HawkUtils().setTempDataCreatePi(
            id = data?.id,
            piNo = data?.piNo,
            date = data?.date,
            title = data?.title,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            years = data?.years,
            statusImplementationModel = data?.statusImplementationModel,
            identification = data?.identification,
            target = data?.target,
            sebabMasalah = data?.sebabMasalah,
            akarMasalah = data?.akarMasalah,
            nilaiOutput = data?.nilaiOutput,
            nqiModel = data?.nqiModel,
            teamMember = current,
            categoryFixing = data?.categoryFixing,
            hasilImplementasi = data?.implementationResult,
            attachment = data?.attachment,
            statusProposal = data?.statusProposal,
            source = source
        )
    }

    fun updateTeamMember(add: ArrayList<TeamMemberItem?>?, source: String) {
        listTeamMember.postValue(add)

        val data = HawkUtils().getTempDataCreatePi(source)

        HawkUtils().setTempDataCreatePi(
            id = data?.id,
            piNo = data?.piNo,
            date = data?.date,
            title = data?.title,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            years = data?.years,
            statusImplementationModel = data?.statusImplementationModel,
            identification = data?.identification,
            target = data?.target,
            sebabMasalah = data?.sebabMasalah,
            akarMasalah = data?.akarMasalah,
            nilaiOutput = data?.nilaiOutput,
            nqiModel = data?.nqiModel,
            teamMember = add,
            categoryFixing = data?.categoryFixing,
            hasilImplementasi = data?.implementationResult,
            attachment = data?.attachment,
            statusProposal = data?.statusProposal,
            source = source
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

    fun addAttachment(add: AttachmentItem, current: ArrayList<AttachmentItem?>?, data: ProjectImprovementCreateModel?, source: String) {
        current?.add(add)

        listAttachment.postValue(current)

        HawkUtils().setTempDataCreatePi(
            id = data?.id,
            piNo = data?.piNo,
            date = data?.date,
            title = data?.title,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            years = data?.years,
            statusImplementationModel = data?.statusImplementationModel,
            identification = data?.identification,
            target = data?.target,
            sebabMasalah = data?.sebabMasalah,
            akarMasalah = data?.akarMasalah,
            nilaiOutput = data?.nilaiOutput,
            nqiModel = data?.nqiModel,
            teamMember = data?.teamMember,
            categoryFixing = data?.categoryFixing,
            hasilImplementasi = data?.implementationResult,
            attachment = current,
            statusProposal = data?.statusProposal,
            source = source
        )
    }

    fun updateAttachment(add: ArrayList<AttachmentItem?>?, data: ProjectImprovementCreateModel?, source: String) {
        listAttachment.postValue(add)

        HawkUtils().setTempDataCreatePi(
            id = data?.id,
            piNo = data?.piNo,
            date = data?.date,
            title = data?.title,
            branch = data?.branch,
            subBranch = data?.subBranch,
            department = data?.department,
            years = data?.years,
            statusImplementationModel = data?.statusImplementationModel,
            identification = data?.identification,
            target = data?.target,
            sebabMasalah = data?.sebabMasalah,
            akarMasalah = data?.akarMasalah,
            nilaiOutput = data?.nilaiOutput,
            nqiModel = data?.nqiModel,
            teamMember = data?.teamMember,
            categoryFixing = data?.categoryFixing,
            hasilImplementasi = data?.implementationResult,
            attachment = add,
            statusProposal = data?.statusProposal,
            source = source
        )
    }

    // === List PI
    private val _listPi = MutableLiveData<Event<LiveData<Result<ResultsResponse<ProjectImprovementModel>>>>>()
    val getListPiItem: LiveData<Event<LiveData<Result<ResultsResponse<ProjectImprovementModel>>>>> get() = _listPi

    fun setListPi(listProjectImprovementRemoteRequest: ProjectImprovementRemoteRequest) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeListPi(listProjectImprovementRemoteRequest) }
            _listPi.value = Event(result)
        }
    }

    // === Detail PI
    private val _detailPi = MutableLiveData<Event<LiveData<Result<ResultsResponse<ProjectImprovementCreateModel>>>>>()
    val getDetailPiItem: LiveData<Event<LiveData<Result<ResultsResponse<ProjectImprovementCreateModel>>>>> get() = _detailPi

    fun setDetailPi(id: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.Default) { repository.observeDetailPi(id, userId) }
            _detailPi.value = Event(result)
        }
    }

    // === Post Submit Create Pi
    private val _postSubmitCreatePi = MutableLiveData<Event<LiveData<Result<ResultsResponse<ProjectImprovementResponseModel>>>>>()
    val postSubmitCreatePi: LiveData<Event<LiveData<Result<ResultsResponse<ProjectImprovementResponseModel>>>>> get() = _postSubmitCreatePi

    fun setPostSubmitCreatePi(projectImprovementCreateModel: ProjectImprovementCreateModel, action: String) {
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default) { repository.observeSubmitCreatePi(projectImprovementCreateModel, action)}
            _postSubmitCreatePi.value = Event(result)
        }
    }

    // === Put Submit Update PI
    private val _postSubmitUpdatePi = MutableLiveData<Event<LiveData<Result<ResultsResponse<ProjectImprovementResponseModel>>>>>()
    val putSubmitUpdatePi: LiveData<Event<LiveData<Result<ResultsResponse<ProjectImprovementResponseModel>>>>> get() = _postSubmitUpdatePi

    fun setPostSubmitUpdatePi(projectImprovementCreateModel: ProjectImprovementCreateModel) {
        viewModelScope.launch(Dispatchers.Main)   {
            val result = withContext(Dispatchers.Default) { repository.observeSubmitUpdatePi(projectImprovementCreateModel)}
                _postSubmitUpdatePi.value = Event(result)
        }
    }

    // === Remove List PI
    private val _removeListPi = MutableLiveData<Event<LiveData<Result<ResultsResponse<ArrayList<String>>>>>>()
    val doRemovePi : LiveData<Event<LiveData<Result<ResultsResponse<ArrayList<String>>>>>> get() = _removeListPi

    fun deletePiList(idPi: Int){
        viewModelScope.launch(Dispatchers.Main){
            val result = withContext(Dispatchers.Default){repository.observeRemovePi(idPi) }
            _removeListPi.value = Event(result)
        }
    }

}