package com.fastrata.eimprovement.utils

import com.fastrata.eimprovement.features.suggestionsystem.data.model.AttachmentItem
import com.fastrata.eimprovement.features.suggestionsystem.data.model.StatusImplementation
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.TeamMemberItem
import com.orhanobut.hawk.Hawk

internal class HawkUtils() {

    private val getDataCreateSs: SuggestionSystemCreateModel? = if (Hawk.contains(SS_CREATE)) Hawk.get(SS_CREATE) else null

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
        teamMember: ArrayList<TeamMemberItem?>? = null,
        attachment: List<AttachmentItem?>? = null,
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
            attachment = attachment ?: getDataCreateSs?.attachment,
        )

        Hawk.put(SS_CREATE, data)
        println("### Hawk : $getDataCreateSs")
    }

    fun getTempDataCreateSs(): SuggestionSystemCreateModel? {
        return getDataCreateSs
    }



}
