package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.features.approval.data.model.ApprovalHistoryStatusModel
import com.fastrata.eimprovement.featuresglobal.data.model.AttachmentItem
import com.fastrata.eimprovement.featuresglobal.data.model.CategoryImprovementItem
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposalItem
import com.fastrata.eimprovement.featuresglobal.data.model.TeamMemberItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectImprovementCreateModel(
    @SerializedName("PI_H_ID")
    var id : Int?,
    @SerializedName("PI_NO")
    var piNo: String?,
    @SerializedName("USER_ID")
    var userId: Int?,
    @SerializedName("NIK")
    var nik: String?,
    @SerializedName("ORG_ID")
    var orgId: Int?,
    @SerializedName("WAREHOUSE_ID")
    var warehouseId: Int?,
    @SerializedName("HEAD_ID")
    var headId: Int?,
    @SerializedName("HEAD_NAME")
    var directMgr: String?,
    @SerializedName("HEAD_NIK")
    var directMgrNik: String?,
    @SerializedName("DEPARTMENT")
    var department: String?,
    @SerializedName("YEAR")
    var years: String?,
    @SerializedName("CREATED_DATE")
    var date: String?,
    @SerializedName("BRANCH_CODE")
    var branchCode: String?,
    @SerializedName("BRANCH")
    var branch: String?,
    @SerializedName("SUBBRANCH")
    var subBranch: String?,
    @SerializedName("TITLE")
    var title: String?,
    @SerializedName("STATUS_IMPLEMENTATION")
    var statusImplementationModel: StatusImplementationPiModel? = null,
    @SerializedName("IDENTIFICATION_PROBLEM")
    var identification: String?,
    @SerializedName("TARGET")
    var target: String?,
    @SerializedName("SEBAB_MASALAH")
    var sebabMasalah: ArrayList<SebabMasalahModel?>?,
    @SerializedName("AKAR_MASALAH")
    var akarMasalah: ArrayList<AkarMasalahModel?>?,
    @SerializedName("BENEFIT")
    var nilaiOutput : String?,
    @SerializedName("NQI")
    var nqiModel : NqiModel? = null,
    @SerializedName("TEAM_MEMBER")
    var teamMember: ArrayList<TeamMemberItem?>?,
    @SerializedName("CATEGORY_IMPROVEMENT")
    var categoryFixing : ArrayList<CategoryImprovementItem?>?,
    @SerializedName("IMP_RESULT")
    var implementationResult : String?,
    @SerializedName("ATTACHMENTS")
    var attachment: ArrayList<AttachmentItem?>?,
    @SerializedName("STATUS_PROPOSAL")
    var statusProposal: StatusProposalItem?,
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