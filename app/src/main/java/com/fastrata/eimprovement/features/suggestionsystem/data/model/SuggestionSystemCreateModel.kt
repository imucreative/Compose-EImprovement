package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.features.approval.data.model.ApprovalHistoryStatusModel
import com.fastrata.eimprovement.featuresglobal.data.model.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuggestionSystemCreateModel(
    @SerializedName("SS_H_ID")
    var id: Int?,
    @SerializedName("SS_NO")
	var ssNo: String?,
    @SerializedName("CREATED_DATE")
    var date: String?,
    @SerializedName("FULL_NAME")
	var name: String?,
    @SerializedName("USER_ID")
    var userId: Int?,
    @SerializedName("NIK")
	var nik: String?,
    @SerializedName("STATUS_IMPLEMENTATION")
	var statusImplementation: StatusImplementation?,
    @SerializedName("TITLE")
	var title: String?,
    @SerializedName("ORG_ID")
    var orgId: Int?,
    @SerializedName("WAREHOUSE_ID")
    var warehouseId: Int?,
    @SerializedName("BRANCH_CODE")
	var branchCode: String?,
    @SerializedName("BRANCH")
    var branch: String?,
    @SerializedName("SUBBRANCH")
    var subBranch: String?,
    @SerializedName("HEAD_ID")
	var headId: Int?,
    @SerializedName("HEAD_NAME")
	var directMgr: String?,
    @SerializedName("HEAD_NIK")
    var directMgrNik: String?,
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
    var statusProposal: StatusProposalItem?,
    @SerializedName("PROSES_PELAKSANAAN")
    var proses :String?,
    @SerializedName("RESULT_IMPLEMENTASI")
    var result : String?,
    @SerializedName("HISTORY_APPROVAL")
    var historyApproval : ArrayList<ApprovalHistoryStatusModel?>?,
    @SerializedName("SCORE")
    var score : Int?,
    @SerializedName("ACTIVITY_TYPE")
    var activityType : String?,     // SS/PI/RP
    @SerializedName("SUBMIT_TYPE")
    var submitType : Int?,          // 1=Setujui, 2=Revisi, 3=Ditolak
    @SerializedName("COMMENT")
    var comment : String?
) : Parcelable
