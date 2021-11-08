package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.AttachmentItem
import com.fastrata.eimprovement.featuresglobal.data.model.CategoryImprovementItem
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.featuresglobal.data.model.TeamMemberItem
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuggestionSystemCreateModel(
    @SerializedName("SS_NO")
	var ssNo: String?,
    @SerializedName("CREATED_DATE")
    var date: String?,
    @SerializedName("FULL_NAME")
	var name: String?,
    @SerializedName("NIK")
	var nik: String?,
    @SerializedName("STATUS_IMPLEMENTATION")
	var statusImplementation: StatusImplementation?,
    @SerializedName("TITLE")
	var title: String?,
    @SerializedName("BRANCH_CODE")
	var branchCode: String?,
    @SerializedName("BRANCH")
    var branch: String?,
    @SerializedName("SUBBRANCH")
    var subBranch: String?,
    @SerializedName("HEAD_NAME")
	var directMgr: String?,
    @SerializedName("PROBLEM")
	var problem: String?,
    @SerializedName("SUGGESTION")
	var suggestion: String?,
    @SerializedName("ATTACHMENTS")
	var attachment: ArrayList<AttachmentItem?>?,
    @SerializedName("CATEGORY_SUGGESTION")
	var categoryImprovement: ArrayList<CategoryImprovementItem?>?,
    @SerializedName("DEPARTMENT")
	var department: String?,
    @SerializedName("TEAM_MEMBER")
	var teamMember: ArrayList<TeamMemberItem?>?,
    @SerializedName("STATUS_PROPOSAL")
    var statusProposal: StatusProposalItem?
) : Parcelable
