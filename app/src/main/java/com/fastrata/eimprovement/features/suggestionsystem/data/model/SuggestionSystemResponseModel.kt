package com.fastrata.eimprovement.features.suggestionsystem.data.model

import com.google.gson.annotations.SerializedName

data class SuggestionSystemResponseModel(
    @SerializedName("sim_trx_suggestion_system_h")
    var simTrxSuggestionSystemH: ArrayList<SimTrxSuggestionSystemH>,
    @SerializedName("sim_trx_suggestion_system_d_team")
    var simTrxSuggestionSystemDTeam: ArrayList<SimTrxSuggestionSystemDTeam>,
    @SerializedName("sim_trx_suggestion_system_d_category")
    var simTrxSuggestionSystemDCategory: ArrayList<SimTrxSuggestionSystemDCategory>,
    @SerializedName("sim_trx_suggestion_system_d_attach")
    var simTrxSuggestionSystemDAttach: ArrayList<SimTrxSuggestionSystemDAttach>,
)

data class SimTrxSuggestionSystemH(
    @SerializedName("SS_H_ID")
    var sshId: Int?,
    @SerializedName("STATUS")
    var status: String?,
)

data class SimTrxSuggestionSystemDTeam(
    @SerializedName("SS_H_ID")
    var sshId: Int?,
    @SerializedName("SS_D_ID_TEAM")
    var ssdIdTeam: Int?,
    @SerializedName("STATUS")
    var status: String?,
)

data class SimTrxSuggestionSystemDCategory(
    @SerializedName("SS_H_ID")
    var sshId: Int?,
    @SerializedName("SS_D_CAT_ID")
    var ssdIdCat: Int?,
    @SerializedName("STATUS")
    var status: String?,
)

data class SimTrxSuggestionSystemDAttach(
    @SerializedName("SS_H_ID")
    var sshId: Int?,
    @SerializedName("ATTACH_ID")
    var ssdIdAttach: Int?,
    @SerializedName("STATUS")
    var status: String?,
)
