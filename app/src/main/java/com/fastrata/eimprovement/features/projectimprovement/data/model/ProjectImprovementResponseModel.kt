package com.fastrata.eimprovement.features.projectimprovement.data.model

import com.google.gson.annotations.SerializedName

data class ProjectImprovementResponseModel (
    @SerializedName("sim_trx_project_improvement_h")
    var simTrxProjectImprovementH: ArrayList<SimProjectImprovementH>,
    @SerializedName("sim_trx_project_improvement_d_team")
    var simTrxProjectImprovementDTeam: ArrayList<SimTrxProjectImprovementDTeam>,
    @SerializedName("sim_trx_project_improvement_d_category")
    var simTrxProjectImprovementDCategory: ArrayList<SimTrxProjectImprovementDCategory>,
    @SerializedName("sim_trx_project_improvement_d_attach")
    var simTrxProjectImprovementDAttach: ArrayList<SimTrxProjectImprovementDAttach>,
    @SerializedName("sim_trx_project_improvement_d_nqi")
    var SimTrxProjectImprovementDNqi : ArrayList<SimTrxProjectImprovementDNqi>,
    @SerializedName("sim_trx_project_improvement_d_problem")
    var SimTrxProjectImprovementDProblem : ArrayList<SimTrxProjectImprovementDProblem>
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

data class SimTrxProjectImprovementDNqi(
    @SerializedName("PI_H_ID")
    var pihId: Int?,
    @SerializedName("NQI_ID")
    var nqiId:Int?,
    @SerializedName("STATUS")
    var status: String?,
)

data class SimTrxProjectImprovementDProblem(
    @SerializedName("PI_H_ID")
    var pihId: Int?,
    @SerializedName("PI_D_ID_PROBLEM")
    var piDIdProblem: Int,
    @SerializedName("STATUS")
    var status: String?,
)