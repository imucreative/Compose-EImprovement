package com.fastrata.eimprovement.features.suggestionsystem.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuggestionSystemCreateModel(
	var ssNo: String?,
	var date: String?,
	var name: String?,
	var nik: String?,
	var statusImplementation: StatusImplementation?,
	var title: String?,
	var branch: String?,
	var subBranch: String?,
	var directMgr: String?,
	var problem: String?,
	var suggestion: String?,
	var attachment: List<AttachmentItem?>?,
	var categorySuggestion: ArrayList<String?>?,
	var department: String?,
	var teamMember: ArrayList<TeamMemberItem?>?,
) : Parcelable
