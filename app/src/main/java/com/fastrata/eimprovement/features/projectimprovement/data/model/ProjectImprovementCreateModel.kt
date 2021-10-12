package com.fastrata.eimprovement.features.projectimprovement.data.model

import android.os.Parcelable
import com.fastrata.eimprovement.featuresglobal.data.model.AttachmentItem
import com.fastrata.eimprovement.featuresglobal.data.model.CategoryImprovementItem
import com.fastrata.eimprovement.featuresglobal.data.model.StatusProposal
import com.fastrata.eimprovement.featuresglobal.data.model.TeamMemberItem
import com.google.gson.annotations.SerializedName

import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectImprovementCreateModel(
    @SerializedName("id")
    var id : Int?,
    @SerializedName("pi_no")
    var piNo: String?,
    @SerializedName("department")
    var department: String?,
    @SerializedName("tahun")
    var years: String?,
    @SerializedName("tgl_pengajuan")
    var date: String?,
    @SerializedName("BRANCH_CODE")
    var branchCode: String?,
    @SerializedName("cabang")
    var branch: String?,
    @SerializedName("subcabang")
    var subBranch: String?,
    @SerializedName("judul")
    var title: String?,
    @SerializedName("status_implementasi")
    var statusImplementation: StatusImplementationPi? = null,
    @SerializedName("identifikasi")
    var identification: String?,
    @SerializedName("penetapan_target")
    var target: String?,
    @SerializedName("sebab_akar_masalah")
    var sebabMasalah: ArrayList<SebabMasalahItem?>?,
    @SerializedName("saran_akar_masalah")
    var akarMasalah: ArrayList<AkarMasalahItem?>?,
    @SerializedName("nilai_output_diharapkan")
    var nilaiOutput : String?,
    @SerializedName("perhitungan_nqi")
    var nqi : NqiModel? = null,
    @SerializedName("anggota_tim")
    var teamMember: ArrayList<TeamMemberItem?>?,
    @SerializedName("kategori_perbaikan")
    var categoryFixing : ArrayList<CategoryImprovementItem?>?,
    @SerializedName("hasil_implementasi")
    var implementationResult : String?,
    @SerializedName("lampiran_pi")
    var attachment: ArrayList<AttachmentItem?>?,
    var statusProposal: StatusProposal?
) : Parcelable