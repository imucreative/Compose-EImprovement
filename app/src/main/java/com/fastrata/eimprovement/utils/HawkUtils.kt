package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.changespoint.data.model.*
import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.projectimprovement.data.model.*
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.orhanobut.hawk.Hawk
import timber.log.Timber

internal class HawkUtils() {

    // init create object
    private val getDataCreateSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_CREATE)) Hawk.get(SS_CREATE) else null
    private val getDataDetailSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_DETAIL_DATA)) Hawk.get(SS_DETAIL_DATA) else null
    private val getDataCreatePi: ProjectImprovementCreateModel? = if (Hawk.contains(PI_CREATE)) Hawk.get(PI_CREATE) else null
    private val getDataCreateCp : ChangePointCreateModel? = if (Hawk.contains(CP_CREATE)) Hawk.get(CP_CREATE) else null

    // Create Suggestion System
    fun setTempDataCreateSs(
        ssNo: String? = null,
        date: String? = null,
        title: String? = null,
        listCategory: ArrayList<CategorySuggestionItem?>? = if (getDataCreateSs?.categorySuggestion == null) arrayListOf() else null,
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
        source: String = SS_CREATE
    ) {
        val data = SuggestionSystemCreateModel(
            ssNo = ssNo ?: if (source == SS_CREATE) getDataCreateSs?.ssNo else getDataDetailSs?.ssNo,
            date = date ?: if (source == SS_CREATE) getDataCreateSs?.date else getDataDetailSs?.date,
            title = title ?: if (source == SS_CREATE) getDataCreateSs?.title else getDataDetailSs?.title,
            categorySuggestion = listCategory ?: if (source == SS_CREATE) getDataCreateSs?.categorySuggestion else getDataDetailSs?.categorySuggestion,
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
            attachment = attachment ?: if (source == SS_CREATE) getDataCreateSs?.attachment else getDataDetailSs?.attachment
        )

        if (source == SS_CREATE) {
            Hawk.put(SS_CREATE, data)
            Timber.w("### Hawk : $data")
        } else {
            Hawk.put(SS_DETAIL_DATA, data)
            Timber.w("### Hawk Detail Data: $data")
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
        year: String? = null,
        statusImplementationpi: StatusImplementationPi? = null,
        indenmasalah: String? = null,
        settarget: String? = null,
        sebabmasalah: ArrayList<SebabMasalahItem?>? = if(getDataCreatePi?.problem == null) arrayListOf() else null,
        akarmasalah: ArrayList<AkarMasalahItem?>? = if (getDataCreatePi?.suggestProblem == null) arrayListOf() else null,
        nilaioutput : String? = null,
        perhitungannqi : NQIModel? = null,
        teammember: ArrayList<TeamMemberItem?>? = if (getDataCreatePi?.teamMember == null) arrayListOf() else null,
        categoryFixingItem: ArrayList<categoryFixingItem?>? = if (getDataCreatePi?.categoryFixing == null) arrayListOf() else null,
        hasilimplementasi : String? = null,
        attachment: ArrayList<AttachmentItem?>? = if (getDataCreatePi?.attachment == null) arrayListOf() else null
    ){
        val data = ProjectImprovementCreateModel(
            id = id ?: getDataCreatePi?.id,
            piNo = piNo ?: getDataCreatePi?.piNo,
            createdDate = date ?: getDataCreatePi?.createdDate,
            title = title ?: getDataCreatePi?.title,
            branch = branch ?: getDataCreatePi?.branch,
            subBranch = subBranch?: getDataCreatePi?.subBranch,
            department = department?: getDataCreatePi?.department,
            years = year?: getDataCreatePi?.years,
            statusImplementation = statusImplementationpi ?: getDataCreatePi?.statusImplementation,
            indentification = indenmasalah ?: getDataCreatePi?.indentification,
            setTarget = settarget ?: getDataCreatePi?.setTarget,
            problem = sebabmasalah ?: getDataCreatePi?.problem,
            suggestProblem = akarmasalah ?:getDataCreatePi?.suggestProblem,
            outputValue = nilaioutput ?:getDataCreatePi?.outputValue,
            nqiTotal = perhitungannqi ?:getDataCreatePi?.nqiTotal,
            teamMember = teammember ?:getDataCreatePi?.teamMember,
            categoryFixing = categoryFixingItem ?:getDataCreatePi?.categoryFixing,
            implementationResult = hasilimplementasi ?:getDataCreatePi?.implementationResult,
            attachment = attachment ?:getDataCreatePi?.attachment
        )
        Hawk.put(PI_CREATE,data)
        Timber.w("### Hawk : $data")
    }

    fun getTempDataCreatePi(): ProjectImprovementCreateModel? {
        return getDataCreatePi
    }

    fun getTempDataCreateCP() : ChangePointCreateModel? {
        return getDataCreateCp
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
        rewarddata: ArrayList<ChangePointRewardItem?>? = if (getDataCreateCp?.penukaran_hadiah == null) arrayListOf() else null,
        riwayat: ArrayList<RiwayatItem?>? = if (getDataCreateCp?.riwayat == null) arrayListOf() else null
    ){
        val data  =ChangePointCreateModel(
            saldo = saldo?: getDataCreateCp?.saldo,
            no_penukaran = cpno?: getDataCreateCp?.no_penukaran,
            nama_pembuat = nama?:getDataCreateCp?.nama_pembuat,
            nik = nik?:getDataCreateCp?.nik,
            cabang = branch?:getDataCreateCp?.cabang,
            department = departement?:getDataCreateCp?.department,
            jabatan = jabatan?:getDataCreateCp?.jabatan,
            tgl_penukaran = date?:getDataCreateCp?.tgl_penukaran,
            keterangan = keterangan?:getDataCreateCp?.keterangan,
            penukaran_hadiah = rewarddata ?: getDataCreateCp?.penukaran_hadiah,
            riwayat = riwayat ?: getDataCreateCp?.riwayat
        )
        Hawk.put(CP_CREATE,data)
        Timber.w("### Hawk : $data")
    }
}
