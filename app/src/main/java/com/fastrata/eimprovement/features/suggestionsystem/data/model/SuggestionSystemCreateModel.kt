package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuggestionSystemCreateModel(
	@SerializedName("no_ss")
	var ssNo: String?,
		var date: String?,
	@SerializedName("nama_pembuat")
	var name: String?,
	@SerializedName("nik")
	var nik: String?,
	@SerializedName("status_implementasi")
	var statusImplementation: StatusImplementation?,
	@SerializedName("judul")
	var title: String?,
	@SerializedName("cabang")
	var branch: String?,
		var subBranch: String?,
	@SerializedName("nama_atasan")
	var directMgr: String?,
	@SerializedName("identifikasi")
	var problem: String?,
	@SerializedName("saran_ide")
	var suggestion: String?,
	@SerializedName("lampiran")
	var attachment: ArrayList<AttachmentItem?>?,
	@SerializedName("kategori")
	var categorySuggestion: ArrayList<CategorySuggestionItem?>?,
	@SerializedName("department")
	var department: String?,
	@SerializedName("anggota_tim")
	var teamMember: ArrayList<TeamMemberItem?>?
) : Parcelable
