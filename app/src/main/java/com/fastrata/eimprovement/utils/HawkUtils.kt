package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.changespoint.data.model.*
import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.fastrata.eimprovement.ui.model.*
import com.orhanobut.hawk.Hawk
import timber.log.Timber

internal class HawkUtils {

    // init create object
    private val getDataCreateSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_CREATE)) Hawk.get(SS_CREATE) else null
    private val getDataDetailSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_DETAIL_DATA)) Hawk.get(SS_DETAIL_DATA) else null
    private val getDataCreatePi: ProjectImprovementCreateModel? = if (Hawk.contains(PI_CREATE)) Hawk.get(PI_CREATE) else null
    private val getDataDetailPi: ProjectImprovementCreateModel? = if (Hawk.contains(PI_DETAIL_DATA)) Hawk.get(PI_DETAIL_DATA) else null
    private val getDataCreateCp : ChangePointCreateModel? = if (Hawk.contains(CP_CREATE)) Hawk.get(CP_CREATE) else null

    // Create Suggestion System
    fun setTempDataCreateSs(
        ssNo: String? = null,
        date: String? = null,
        title: String? = null,
        listCategory: ArrayList<CategoryImprovementItem?>? = if (getDataCreateSs?.categoryImprovement == null) arrayListOf() else null,
        name: String? = null,
        nik: String? = null,
        branch: String? = null,
        subBranch: String? = null,
        department: String? = null,
        directMgr: String? = null,
        suggestion: String? = null,
        problem: String? = null,
        statusImplementation: StatusImplementation? = null,
        teamMember: ArrayList<TeamMemberItem?>? = if (getDataCreateSs?.teamMember == null) arrayListOf() else null,
        attachment: ArrayList<AttachmentItem?>? = if (getDataCreateSs?.attachment == null) arrayListOf() else null,
        statusProposal: StatusProposal? = null,
        source: String = SS_CREATE
    ) {
        val data = SuggestionSystemCreateModel(
            ssNo = ssNo ?: if (source == SS_CREATE) getDataCreateSs?.ssNo else getDataDetailSs?.ssNo,
            date = date ?: if (source == SS_CREATE) getDataCreateSs?.date else getDataDetailSs?.date,
            title = title ?: if (source == SS_CREATE) getDataCreateSs?.title else getDataDetailSs?.title,
            categoryImprovement = listCategory ?: if (source == SS_CREATE) getDataCreateSs?.categoryImprovement else getDataDetailSs?.categoryImprovement,
            name = name ?: if (source == SS_CREATE) getDataCreateSs?.name else getDataDetailSs?.name,
            nik = nik ?: if (source == SS_CREATE) getDataCreateSs?.nik else getDataDetailSs?.nik,
            branch = branch ?: if (source == SS_CREATE) getDataCreateSs?.branch else getDataDetailSs?.branch,
            subBranch = subBranch ?: if (source == SS_CREATE) getDataCreateSs?.subBranch else getDataDetailSs?.subBranch,
            department = department ?: if (source == SS_CREATE) getDataCreateSs?.department else getDataDetailSs?.department,
            directMgr = directMgr ?: if (source == SS_CREATE) getDataCreateSs?.directMgr else getDataDetailSs?.directMgr,
            suggestion = suggestion ?: if (source == SS_CREATE) getDataCreateSs?.suggestion else getDataDetailSs?.suggestion,
            problem = problem ?: if (source == SS_CREATE) getDataCreateSs?.problem else getDataDetailSs?.problem,
            statusImplementation = statusImplementation ?: if (source == SS_CREATE) getDataCreateSs?.statusImplementation else getDataDetailSs?.statusImplementation,
            teamMember = teamMember ?: if (source == SS_CREATE) getDataCreateSs?.teamMember else getDataDetailSs?.teamMember,
            attachment = attachment ?: if (source == SS_CREATE) getDataCreateSs?.attachment else getDataDetailSs?.attachment,
            statusProposal = statusProposal ?: if (source == SS_CREATE) getDataCreateSs?.statusProposal else getDataDetailSs?.statusProposal
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

    fun setDataLogin(loginEntity: LoginEntity? = null) {
        Hawk.put(HAWK_USER, loginEntity)
    }

    fun getDataLogin(): LoginEntity {
        return Hawk.get(HAWK_USER)
    }

    fun setTempDataCreatePi(
        id : Int? = 0,
        piNo : String? = null,
        date: String? = null,
        title: String? = null,
        branch: String? = null,
        subBranch: String? = null,
        department: String? = null,
        years: String? = null,
        statusImplementation: StatusImplementationPi? = null,
        identification: String? = null,
        target: String? = null,
        sebabMasalah: ArrayList<SebabMasalahItem?>? = if(getDataCreatePi?.problem == null) arrayListOf() else null,
        akarMasalah: ArrayList<AkarMasalahItem?>? = if (getDataCreatePi?.akarMasalah == null) arrayListOf() else null,
        nilaiOutput : String? = null,
        perhitunganNqi : NqiModel? = null,
        teamMember: ArrayList<TeamMemberItem?>? = if (getDataCreatePi?.teamMember == null) arrayListOf() else null,
        categoryFixingItem: ArrayList<CategoryImprovementItem?>? = if (getDataCreatePi?.categoryFixing == null) arrayListOf() else null,
        hasilImplementasi : String? = null,
        attachment: ArrayList<AttachmentItem?>? = if (getDataCreatePi?.attachment == null) arrayListOf() else null,
        statusProposal: StatusProposal? = null,
        source: String = PI_CREATE
    ){
        val data = ProjectImprovementCreateModel(
            id = id ?: if (source == PI_CREATE) getDataCreatePi?.id else getDataDetailPi?.id,
            piNo = piNo ?: if (source == PI_CREATE) getDataCreatePi?.piNo else getDataDetailPi?.piNo,
            createdDate = date ?: if (source == PI_CREATE) getDataCreatePi?.createdDate else getDataDetailPi?.createdDate,
            title = title ?: if (source == PI_CREATE) getDataCreatePi?.title else getDataDetailPi?.title,
            branch = branch ?: if (source == PI_CREATE) getDataCreatePi?.branch else getDataDetailPi?.branch,
            subBranch = subBranch?: if (source == PI_CREATE) getDataCreatePi?.subBranch else getDataDetailPi?.subBranch,
            department = department?: if (source == PI_CREATE) getDataCreatePi?.department else getDataDetailPi?.department,
            years = years?: if (source == PI_CREATE) getDataCreatePi?.years else getDataDetailPi?.years,
            statusImplementation = statusImplementation ?: if (source == PI_CREATE) getDataCreatePi?.statusImplementation else getDataDetailPi?.statusImplementation,
            identification = identification ?: if (source == PI_CREATE) getDataCreatePi?.identification else getDataDetailPi?.identification,
            setTarget = target ?: if (source == PI_CREATE) getDataCreatePi?.setTarget else getDataDetailPi?.setTarget,
            problem = sebabMasalah ?: if (source == PI_CREATE) getDataCreatePi?.problem else getDataDetailPi?.problem,
            akarMasalah = akarMasalah ?: if (source == PI_CREATE) getDataCreatePi?.akarMasalah else getDataDetailPi?.akarMasalah,
            outputValue = nilaiOutput ?: if (source == PI_CREATE) getDataCreatePi?.outputValue else getDataDetailPi?.outputValue,
            nqi = perhitunganNqi ?: if (source == PI_CREATE) getDataCreatePi?.nqi else getDataDetailPi?.nqi,
            teamMember = teamMember ?: if (source == PI_CREATE) getDataCreatePi?.teamMember else getDataDetailPi?.teamMember,
            categoryFixing = categoryFixingItem ?: if (source == PI_CREATE) getDataCreatePi?.categoryFixing else getDataDetailPi?.categoryFixing,
            implementationResult = hasilImplementasi ?: if (source == PI_CREATE) getDataCreatePi?.implementationResult else getDataDetailPi?.implementationResult,
            attachment = attachment ?: if (source == PI_CREATE) getDataCreatePi?.attachment else getDataDetailPi?.attachment,
            statusProposal = statusProposal ?: if (source == PI_CREATE) getDataCreatePi?.statusProposal else getDataDetailPi?.statusProposal
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
        saldo : Int?= 0,
        cpno: String? = null,
        nama: String? = null,
        nik: String? = null,
        branch: String? = null,
        departement: String? = null,
        jabatan: String? = null,
        date: String? = null,
        keterangan: String? = null,
        rewarddata: ArrayList<ChangePointRewardItem?>? = if (getDataCreateCp?.reward == null) arrayListOf() else null,
        riwayat: ArrayList<RiwayatItem?>? = if (getDataCreateCp?.history == null) arrayListOf() else null
    ){
        val data  =ChangePointCreateModel(
            saldo = saldo?: getDataCreateCp?.saldo,
            cpNo = cpno?: getDataCreateCp?.cpNo,
            name = nama?:getDataCreateCp?.name,
            nik = nik?:getDataCreateCp?.nik,
            branch = branch?:getDataCreateCp?.branch,
            department = departement?:getDataCreateCp?.department,
            job = jabatan?:getDataCreateCp?.job,
            date = date?:getDataCreateCp?.date,
            description = keterangan?:getDataCreateCp?.description,
            reward = rewarddata ?: getDataCreateCp?.reward,
            history = riwayat ?: getDataCreateCp?.history
        )
        Hawk.put(CP_CREATE,data)
        Timber.w("### Hawk : $data")
    }

    fun getTempDataCreateCP() : ChangePointCreateModel? {
        return getDataCreateCp
    }
}
