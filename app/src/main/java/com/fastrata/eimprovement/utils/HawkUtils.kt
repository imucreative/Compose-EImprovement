package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.StatusImplementation
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem
import com.orhanobut.hawk.Hawk
import timber.log.Timber

internal class HawkUtils() {

    // init create object
    private val getDataCreateSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_CREATE)) Hawk.get(SS_CREATE) else null

    // Create Suggestion System
    fun setTempDataCreateSs(
        ssNo: String? = null,
        date: String? = null,
        title: String? = null,
        listCategory: ArrayList<String?>? = null,
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
}
