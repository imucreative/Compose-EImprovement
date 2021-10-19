package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
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
    @SerializedName("DEPARTMENT")
    var department: String?,
    @SerializedName("tahun")
    var years: String?,
    @SerializedName("tgl_pengajuan")
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
    var statusProposal: StatusProposalItem?
) : Parcelable