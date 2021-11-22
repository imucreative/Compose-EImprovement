package com.fastrata.eimprovement.features.changespoint.data.model

import com.google.gson.annotations.SerializedName

data class ChangePointResponseModel (
@SerializedName("sim_trx_redeem_point_h")
var simTrxRedeemPointSystemH : ArrayList<SimTrxRedeemPointSystemH>,
)

data class SimTrxRedeemPointSystemH(
    @SerializedName("RP_H_ID")
    var rphId : Int?,
    @SerializedName("STATUS")
    var status: String?,
)
