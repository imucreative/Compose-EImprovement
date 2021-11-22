package com.fastrata.eimprovement.features.projectimprovement.data.model

import com.google.gson.annotations.SerializedName

data class ProjectImprovementResponseModel (
    @SerializedName("")
    var simTrxProjectImprovementH: ArrayList<SimProjectImprovementH>,
    @SerializedName("")
    var simTrxProjectImprovementDTeam: ArrayList<SimTrxProjectImprovementDTeam>,
    @SerializedName("")
    var simTrxProjectImprovementDCategory: ArrayList<SimTrxProjectImprovementDCategory>,
    @SerializedName("")
    var simTrxProjectImprovementDAttach: ArrayList<SimTrxProjectImprovementDAttach>,

    )

data class SimProjectImprovementH(
    @SerializedName("PI_H_ID")
    var pihId: Int?,
    @SerializedName("STATUS")
    var status: String?,
)

data class SimTrxProjectImprovementDTeam(
    @SerializedName("PI_H_ID")
    var pihId: Int?,
    @SerializedName("PI_D_ID_TEAM")
    var piIdTeam: Int?,
    @SerializedName("STATUS")
    var status: String?,
)

data class SimTrxProjectImprovementDCategory(
    @SerializedName("PI_H_ID")
    var  pihId: Int?,
    @SerializedName("PI_D_CAT_ID")
    var piIdCat: Int?,
    @SerializedName("STATUS")
    var status: String?,

)

data class SimTrxProjectImprovementDAttach(
    @SerializedName("PI_H_ID")
    var  pihId: Int?,
    @SerializedName("ATTACH_ID")
    var piIdAttach: Int?,
    @SerializedName("STATUS")
    var status: String?,
)