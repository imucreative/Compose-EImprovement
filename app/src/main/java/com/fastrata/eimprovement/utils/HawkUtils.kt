package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.projectimprovement.data.model.AkarMasalahItem
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.SebabMasalahItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.*
import com.orhanobut.hawk.Hawk
import timber.log.Timber

internal class HawkUtils() {

    // init create object
    private val getDataCreateSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_CREATE)) Hawk.get(SS_CREATE) else null
    private val getDataCreatePi: ProjectImprovementCreateModel? = if (Hawk.contains(PI_CREATE)) Hawk.get(PI_CREATE) else null

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
        attachment: ArrayList<AttachmentItem?>? = if (getDataCreateSs?.attachment == null) arrayListOf() else null
    ) {
        val data = SuggestionSystemCreateModel(
            ssNo = ssNo ?: getDataCreateSs?.ssNo,
            date = date ?: getDataCreateSs?.date,
            title = title ?: getDataCreateSs?.title,
            categorySuggestion = listCategory ?: getDataCreateSs?.categorySuggestion,
            name = name ?: getDataCreateSs?.name,
            nik = nik ?: getDataCreateSs?.nik,
            branch = branch ?: getDataCreateSs?.branch,
            subBranch = subBranch ?: getDataCreateSs?.subBranch,
            department = department ?: getDataCreateSs?.department,
            directMgr = directMgr ?: getDataCreateSs?.directMgr,
            suggestion = suggestion ?: getDataCreateSs?.suggestion,
            problem = problem ?: getDataCreateSs?.problem,
            statusImplementation = statusImplementation ?: getDataCreateSs?.statusImplementation,
            teamMember = teamMember ?: getDataCreateSs?.teamMember,
            attachment = attachment ?: getDataCreateSs?.attachment
        )

        Hawk.put(SS_CREATE, data)
        Timber.w("### Hawk : $data")
    }

    fun getTempDataCreateSs(): SuggestionSystemCreateModel? {
        return getDataCreateSs
    }

    fun setLoginBoolean(value: Boolean): Boolean {
        return Hawk.put(SUCCES_LOGIN,value)
    }

    fun setSaldo(saldo: String) {
        Hawk.put<Any>(point, saldo)
    }

    fun getSaldo():String {
        return Hawk.get(point)
    }

    fun setTempDataCreatePi(
        piNo : String? = null,
        date: String? = null,
        title: String? = null,
        listCategory: ArrayList<CategorySuggestionItem?>? = if (getDataCreateSs?.categorySuggestion == null) arrayListOf() else null,
        branch: String? = null,
        subBranch: String? = null,
        department: String? = null,
        year: String? = null,
        statusImplementation: StatusImplementation? = null,
        indenmasalah: String? = null,
        settarget: String? = null,
        akarmasalah: ArrayList<AkarMasalahItem?>? = if (getDataCreatePi?.akarmasalah == null) arrayListOf() else null,
        sebabmasalah: ArrayList<SebabMasalahItem?>? = if(getDataCreatePi?.sebabmasalah == null) arrayListOf() else null,
        teammember: ArrayList<TeamMemberItem?>? = if (getDataCreatePi?.teammember == null) arrayListOf() else null,
        attachment: ArrayList<AttachmentItem?>? = if (getDataCreatePi?.attachment == null) arrayListOf() else null
    ){
        val data = ProjectImprovementCreateModel(
            piNo = piNo ?: getDataCreatePi?.piNo,
            date = date ?: getDataCreatePi?.date,
            title = title ?: getDataCreatePi?.title,
            categorySuggestion = listCategory ?: getDataCreateSs?.categorySuggestion,
            branch = branch ?: getDataCreatePi?.branch,
            subBranch = subBranch?: getDataCreatePi?.subBranch,
            department = department?: getDataCreatePi?.department,
            year = year?: getDataCreatePi?.year,
            statusImplementation = statusImplementation ?: getDataCreateSs?.statusImplementation,
            indenmasalah = indenmasalah ?: getDataCreatePi?.indenmasalah,
            settarget = settarget ?: getDataCreatePi?.settarget,
            akarmasalah = akarmasalah ?:getDataCreatePi?.akarmasalah,
            sebabmasalah = sebabmasalah ?: getDataCreatePi?.sebabmasalah,
            attachment = attachment ?: getDataCreatePi?.attachment,
            teammember = teammember ?: getDataCreateSs?.teamMember,
        )
        Hawk.put(PI_CREATE,data)
        Timber.w("### Hawk : $data")
    }

    fun getTempDataCreatePi(): ProjectImprovementCreateModel? {
        return getDataCreatePi
    }
}
