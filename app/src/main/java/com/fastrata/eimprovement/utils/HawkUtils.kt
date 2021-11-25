package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.approval.data.model.ApprovalHistoryStatusModel
import com.fastrata.eimprovement.features.changespoint.data.model.*
import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.fastrata.eimprovement.featuresglobal.data.model.*
import com.orhanobut.hawk.Hawk
import timber.log.Timber

internal class HawkUtils {

    // init create object
    private val getDataCreateSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_CREATE)) Hawk.get(SS_CREATE) else null
    private val getDataDetailSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_DETAIL_DATA)) Hawk.get(SS_DETAIL_DATA) else null
    private val getDataCreatePi: ProjectImprovementCreateModel? = if (Hawk.contains(PI_CREATE)) Hawk.get(PI_CREATE) else null
    private val getDataDetailPi: ProjectImprovementCreateModel? = if (Hawk.contains(PI_DETAIL_DATA)) Hawk.get(PI_DETAIL_DATA) else null
    private val getDataCreateCp : ChangePointCreateModel? = if (Hawk.contains(CP_CREATE)) Hawk.get(CP_CREATE) else null
    private val getDataDetailCp : ChangePointCreateModel? = if (Hawk.contains(CP_DETAIL_DATA)) Hawk.get(CP_DETAIL_DATA) else null

    // Create Suggestion System
    fun setTempDataCreateSs(
        id: Int? = null,
        ssNo: String? = null,
        date: String? = null,
        title: String? = null,
        listCategory: ArrayList<CategoryImprovementItem?>? = if (getDataCreateSs?.categoryImprovement == null) arrayListOf() else null,
        name: String? = null,
        nik: String? = null,
        branchCode: String? = null,
        branch: String? = null,
        subBranch: String? = null,
        department: String? = null,
        directMgr: String? = null,
        suggestion: String? = null,
        problem: String? = null,
        statusImplementation: StatusImplementation? = null,
        teamMember: ArrayList<TeamMemberItem?>? = if (getDataCreateSs?.teamMember == null) arrayListOf() else null,
        attachment: ArrayList<AttachmentItem?>? = if (getDataCreateSs?.attachment == null) arrayListOf() else null,
        statusProposal: StatusProposalItem? = null,
        userId: Int? = null,
        headId: Int? = null,
        orgId: Int? = null,
        warehouseId: Int? = null,
        proses: String? = null,
        result: String? = null,
        historyApproval: ArrayList<ApprovalHistoryStatusModel?>? = if (getDataCreateSs?.historyApproval == null) arrayListOf() else null,
        activityType: String? = null,
        submitType: Int? = null,
        comment: String? = null,
        source: String = SS_CREATE
    ) {
        val data = SuggestionSystemCreateModel(
            id = id ?: if (source == SS_CREATE) getDataCreateSs?.id else getDataDetailSs?.id,
            ssNo = ssNo ?: if (source == SS_CREATE) getDataCreateSs?.ssNo else getDataDetailSs?.ssNo,
            date = date ?: if (source == SS_CREATE) getDataCreateSs?.date else getDataDetailSs?.date,
            title = title ?: if (source == SS_CREATE) getDataCreateSs?.title else getDataDetailSs?.title,
            categoryImprovement = listCategory ?: if (source == SS_CREATE) getDataCreateSs?.categoryImprovement else getDataDetailSs?.categoryImprovement,
            name = name ?: if (source == SS_CREATE) getDataCreateSs?.name else getDataDetailSs?.name,
            nik = nik ?: if (source == SS_CREATE) getDataCreateSs?.nik else getDataDetailSs?.nik,
            branchCode = branchCode ?: if (source == SS_CREATE) getDataCreateSs?.branchCode else getDataDetailSs?.branchCode,
            branch = branch ?: if (source == SS_CREATE) getDataCreateSs?.branch else getDataDetailSs?.branch,
            subBranch = subBranch ?: if (source == SS_CREATE) getDataCreateSs?.subBranch else getDataDetailSs?.subBranch,
            department = department ?: if (source == SS_CREATE) getDataCreateSs?.department else getDataDetailSs?.department,
            directMgr = directMgr ?: if (source == SS_CREATE) getDataCreateSs?.directMgr else getDataDetailSs?.directMgr,
            suggestion = suggestion ?: if (source == SS_CREATE) getDataCreateSs?.suggestion else getDataDetailSs?.suggestion,
            problem = problem ?: if (source == SS_CREATE) getDataCreateSs?.problem else getDataDetailSs?.problem,
            statusImplementation = statusImplementation ?: if (source == SS_CREATE) getDataCreateSs?.statusImplementation else getDataDetailSs?.statusImplementation,
            teamMember = teamMember ?: if (source == SS_CREATE) getDataCreateSs?.teamMember else getDataDetailSs?.teamMember,
            attachment = attachment ?: if (source == SS_CREATE) getDataCreateSs?.attachment else getDataDetailSs?.attachment,
            statusProposal = statusProposal ?: if (source == SS_CREATE) getDataCreateSs?.statusProposal else getDataDetailSs?.statusProposal,
            proses = proses ?: if (source == SS_CREATE) getDataCreateSs?.proses else getDataDetailSs?.proses,
            result = result ?: if (source == SS_CREATE) getDataCreateSs?.result else getDataDetailSs?.result,
            headId = headId ?: if (source == SS_CREATE) getDataCreateSs?.headId else getDataDetailSs?.headId,
            userId = userId ?: if (source == SS_CREATE) getDataCreateSs?.userId else getDataDetailSs?.userId,
            orgId = orgId ?: if (source == SS_CREATE) getDataCreateSs?.orgId else getDataDetailSs?.orgId,
            warehouseId = warehouseId ?: if (source == SS_CREATE) getDataCreateSs?.warehouseId else getDataDetailSs?.warehouseId,
            historyApproval = historyApproval ?: if (source == SS_CREATE) getDataCreateSs?.historyApproval else getDataDetailSs?.historyApproval,
            activityType = activityType ?: if (source == SS_CREATE) getDataCreateSs?.activityType else getDataDetailSs?.activityType,
            submitType = submitType ?: if (source == SS_CREATE) getDataCreateSs?.submitType else getDataDetailSs?.submitType,
            comment = comment ?: if (source == SS_CREATE) getDataCreateSs?.comment else getDataDetailSs?.comment
        )

        if (source == SS_CREATE) {
            Hawk.put(SS_CREATE, data)
            Timber.w("### Hawk SS: $data")
        } else {
            Hawk.put(SS_DETAIL_DATA, data)
            Timber.w("### Hawk SS Detail Data: $data")
        }
    }

    fun getTempDataCreateSs(source: String = SS_CREATE): SuggestionSystemCreateModel? {
        return if (source == SS_CREATE) {
            getDataCreateSs
        } else {
            getDataDetailSs
        }
    }

    fun setStatusLogin(status: Boolean) {
        Hawk.put(HAWK_SUCCESS_LOGIN, status)
    }

    fun getStatusLogin(): Boolean {
        if (Hawk.contains(HAWK_SUCCESS_LOGIN)) {
            return Hawk.get(HAWK_SUCCESS_LOGIN)
        }
        return false
    }

    fun setStatusImplementation(status: Boolean){
        Hawk.put(PI_STATUS_IMPLEMENTATION,status)
    }

    fun getStatusImplementation(): Boolean{
        if (Hawk.contains(PI_STATUS_IMPLEMENTATION)){
            return Hawk.get(PI_STATUS_IMPLEMENTATION)
        }
        return false
    }

    fun setStatusSuggestion(status: Boolean){
        Hawk.put(SS_STATUS_IMPLEMENTATION,status)
    }

    fun getStatusSuggestion(): Boolean{
        if (Hawk.contains(SS_STATUS_IMPLEMENTATION)){
            return Hawk.get(SS_STATUS_IMPLEMENTATION)
        }
        return false
    }

    fun setDataLogin(loginEntity: LoginEntity? = null) {
        Hawk.put(HAWK_USER, loginEntity)
    }

    fun getDataLogin(): LoginEntity {
        return Hawk.get(HAWK_USER)
    }

    fun setDataBalance(value : Int){
        Hawk.put(HAWK_BALANCE,value)
    }

    fun getDataBalance(): Int {
        return Hawk.get(HAWK_BALANCE)
    }

    fun setTempDataCreatePi(
        id : Int? = 0,
        piNo : String? = null,
        date: String? = null,
        title: String? = null,
        branchCode: String? = null,
        branch: String? = null,
        subBranch: String? = null,
        department: String? = null,
        years: String? = null,
        statusImplementationModel: StatusImplementationPiModel? = null,
        identification: String? = null,
        target: String? = null,
        sebabMasalah: ArrayList<SebabMasalahModel?>? = if(getDataCreatePi?.sebabMasalah == null) arrayListOf() else null,
        akarMasalah: ArrayList<AkarMasalahModel?>? = if (getDataCreatePi?.akarMasalah == null) arrayListOf() else null,
        nilaiOutput : String? = null,
        nqiModel : NqiModel? = null,
        teamMember: ArrayList<TeamMemberItem?>? = if (getDataCreatePi?.teamMember == null) arrayListOf() else null,
        categoryFixing: ArrayList<CategoryImprovementItem?>? = if (getDataCreatePi?.categoryFixing == null) arrayListOf() else null,
        hasilImplementasi : String? = null,
        attachment: ArrayList<AttachmentItem?>? = if (getDataCreatePi?.attachment == null) arrayListOf() else null,
        statusProposal: StatusProposalItem? = null,
        nik: String? = null,
        userId: Int? = null,
        headId: Int? = null,
        orgId: Int? = null,
        warehouseId: Int? = null,
        historyApproval: ArrayList<ApprovalHistoryStatusModel?>? = if (getDataCreatePi?.historyApproval == null) arrayListOf() else null,
        activityType: String? = null,
        submitType: Int? = null,
        comment: String? = null,
        source: String = PI_CREATE
    ){
        val data = ProjectImprovementCreateModel(
            id = id ?: if (source == PI_CREATE) getDataCreatePi?.id else getDataDetailPi?.id,
            piNo = piNo ?: if (source == PI_CREATE) getDataCreatePi?.piNo else getDataDetailPi?.piNo,
            date = date ?: if (source == PI_CREATE) getDataCreatePi?.date else getDataDetailPi?.date,
            title = title ?: if (source == PI_CREATE) getDataCreatePi?.title else getDataDetailPi?.title,
            branchCode = branchCode ?: if (source == PI_CREATE) getDataCreatePi?.branchCode else getDataDetailPi?.branchCode,
            branch = branch ?: if (source == PI_CREATE) getDataCreatePi?.branch else getDataDetailPi?.branch,
            subBranch = subBranch?: if (source == PI_CREATE) getDataCreatePi?.subBranch else getDataDetailPi?.subBranch,
            department = department?: if (source == PI_CREATE) getDataCreatePi?.department else getDataDetailPi?.department,
            years = years?: if (source == PI_CREATE) getDataCreatePi?.years else getDataDetailPi?.years,
            statusImplementationModel = statusImplementationModel ?: if (source == PI_CREATE) getDataCreatePi?.statusImplementationModel else getDataDetailPi?.statusImplementationModel,
            identification = identification ?: if (source == PI_CREATE) getDataCreatePi?.identification else getDataDetailPi?.identification,
            target = target ?: if (source == PI_CREATE) getDataCreatePi?.target else getDataDetailPi?.target,
            sebabMasalah = sebabMasalah ?: if (source == PI_CREATE) getDataCreatePi?.sebabMasalah else getDataDetailPi?.sebabMasalah,
            akarMasalah = akarMasalah ?: if (source == PI_CREATE) getDataCreatePi?.akarMasalah else getDataDetailPi?.akarMasalah,
            nilaiOutput = nilaiOutput ?: if (source == PI_CREATE) getDataCreatePi?.nilaiOutput else getDataDetailPi?.nilaiOutput,
            nqiModel = nqiModel ?: if (source == PI_CREATE) getDataCreatePi?.nqiModel else getDataDetailPi?.nqiModel,
            teamMember = teamMember ?: if (source == PI_CREATE) getDataCreatePi?.teamMember else getDataDetailPi?.teamMember,
            categoryFixing = categoryFixing ?: if (source == PI_CREATE) getDataCreatePi?.categoryFixing else getDataDetailPi?.categoryFixing,
            implementationResult = hasilImplementasi ?: if (source == PI_CREATE) getDataCreatePi?.implementationResult else getDataDetailPi?.implementationResult,
            attachment = attachment ?: if (source == PI_CREATE) getDataCreatePi?.attachment else getDataDetailPi?.attachment,
            statusProposal = statusProposal ?: if (source == PI_CREATE) getDataCreatePi?.statusProposal else getDataDetailPi?.statusProposal,

            nik = nik ?: if (source == PI_CREATE) getDataCreatePi?.nik else getDataDetailPi?.nik,
            headId = headId ?: if (source == PI_CREATE) getDataCreatePi?.headId else getDataDetailPi?.headId,
            userId = userId ?: if (source == PI_CREATE) getDataCreatePi?.userId else getDataDetailPi?.userId,
            orgId = orgId ?: if (source == PI_CREATE) getDataCreatePi?.orgId else getDataDetailPi?.orgId,
            warehouseId = warehouseId ?: if (source == PI_CREATE) getDataCreatePi?.warehouseId else getDataDetailPi?.warehouseId,
            historyApproval = historyApproval ?: if (source == PI_CREATE) getDataCreatePi?.historyApproval else getDataDetailPi?.historyApproval,
            activityType = activityType ?: if (source == PI_CREATE) getDataCreatePi?.activityType else getDataDetailPi?.activityType,
            submitType = submitType ?: if (source == PI_CREATE) getDataCreatePi?.submitType else getDataDetailPi?.submitType,
            comment = comment ?: if (source == PI_CREATE) getDataCreatePi?.comment else getDataDetailPi?.comment
        )

        if (source == PI_CREATE) {
            Hawk.put(PI_CREATE, data)
            Timber.w("### Hawk PI: $data")
        } else {
            Hawk.put(PI_DETAIL_DATA, data)
            Timber.w("### Hawk PI Detail Data: $data")
        }
    }

    fun getTempDataCreatePi(source: String = PI_CREATE): ProjectImprovementCreateModel? {
        return if (source == PI_CREATE) {
            getDataCreatePi
        } else {
            getDataDetailPi
        }
    }

    fun setTempDataCreateCp(
        id : Int?= 0,
        saldo : Int?= 0,
        cpNo: String? = null,
        name: String? = null,
        nik: String? = null,
        branch: String? = null,
        subBranch: String? = null,
        departement: String? = null,
        position: String? = null,
        date: String? = null,
        keterangan: String? = null,
        rewardData: ArrayList<RewardItem?>? = if (getDataCreateCp?.reward == null) arrayListOf() else null,
        statusProposal: StatusProposalItem? = null,
        historyApproval: ArrayList<ApprovalHistoryStatusModel?>? = if (getDataCreateCp?.historyApproval == null) arrayListOf() else null,
        userId: Int? = null,
        headId: Int? = null,
        orgId: Int? = null,
        warehouseId: Int? = null,
        activityType: String? = null,
        submitType: Int? = null,
        comment: String? = null,
        branchCode: String? = null,
        source: String = CP_CREATE
    ){
        val data = ChangePointCreateModel(
            id = id ?: if (source == CP_CREATE) getDataCreateCp?.id else getDataDetailCp?.id,
            cpNo = cpNo?: if(source == CP_CREATE) getDataCreateCp?.cpNo else getDataDetailCp?.cpNo,
            saldo = saldo?: if(source == CP_CREATE) getDataCreateCp?.saldo else getDataDetailCp?.saldo,
            name = name?: if(source == CP_CREATE) getDataCreateCp?.name else getDataDetailCp?.name,
            nik = nik?: if(source == CP_CREATE) getDataCreateCp?.nik else getDataDetailCp?.nik,
            branch = branch?: if(source == CP_CREATE) getDataCreateCp?.branch else getDataDetailCp?.branch,
            subBranch = subBranch?: if(source == CP_CREATE) getDataCreateCp?.subBranch else getDataDetailCp?.subBranch,
            department = departement?: if(source == CP_CREATE) getDataCreateCp?.department else getDataDetailCp?.department,
            position = position?: if(source == CP_CREATE) getDataCreateCp?.position else getDataDetailCp?.position,
            date = date?: if(source == CP_CREATE) getDataCreateCp?.date else getDataDetailCp?.date,
            description = keterangan?: if(source == CP_CREATE) getDataCreateCp?.description else getDataDetailCp?.description,
            reward = rewardData?: if(source == CP_CREATE) getDataCreateCp?.reward else getDataDetailCp?.reward,

            statusProposal = statusProposal ?: if (source == CP_CREATE) getDataCreateCp?.statusProposal else getDataDetailCp?.statusProposal,
            headId = headId ?: if (source == CP_CREATE) getDataCreateCp?.headId else getDataDetailCp?.headId,
            userId = userId ?: if (source == CP_CREATE) getDataCreateCp?.userId else getDataDetailCp?.userId,
            orgId = orgId ?: if (source == CP_CREATE) getDataCreateCp?.orgId else getDataDetailCp?.orgId,
            warehouseId = warehouseId ?: if (source == CP_CREATE) getDataCreateCp?.warehouseId else getDataDetailCp?.warehouseId,
            historyApproval = historyApproval ?: if (source == CP_CREATE) getDataCreateCp?.historyApproval else getDataDetailCp?.historyApproval,
            activityType = activityType ?: if (source == CP_CREATE) getDataCreateCp?.activityType else getDataDetailCp?.activityType,
            submitType = submitType ?: if (source == CP_CREATE) getDataCreateCp?.submitType else getDataDetailCp?.submitType,
            branchCode = branchCode ?: if (source == CP_CREATE) getDataCreateCp?.branchCode else getDataDetailCp?.branchCode,
            comment = comment ?: if (source == CP_CREATE) getDataCreateCp?.comment else getDataDetailCp?.comment
        )
        if (source == CP_CREATE) {
            Hawk.put(CP_CREATE, data)
            Timber.w("### Hawk CP: $data")
        } else {
            Hawk.put(CP_DETAIL_DATA, data)
            Timber.w("### Hawk CP Detail Data: $data")
        }
    }

    fun getTempDataCreateCp(source: String = CP_CREATE) : ChangePointCreateModel? {
        return if (source == CP_CREATE){
            getDataCreateCp
        }else{
            getDataDetailCp
        }
    }

    fun removeDataCreateProposal(typeProposal: String){
        Hawk.delete(typeProposal)
    }
}
