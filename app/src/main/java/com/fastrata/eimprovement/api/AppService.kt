package com.fastrata.eimprovement.api

import com.fastrata.eimprovement.features.changespoint.data.model.GiftItem
import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.login.data.model.LoginRemoteRequest
import com.fastrata.eimprovement.featuresglobal.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * App REST API access points
 */
interface AppService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRemoteRequest): Response<ResultsResponse<LoginEntity>>

    @GET("master/category")
    suspend fun listCategory(): Response<ResultsResponse<CategoryImprovementItem>>

    @GET("master/teammember/{BRANCH_CODE}")
    suspend fun listTeamMember(@Path("BRANCH_CODE") branchCode: String): Response<ResultsResponse<MemberNameItem>>

    @GET("master/department")
    suspend fun listDepartment(): Response<ResultsResponse<MemberDepartmentItem>>

    @GET("master/teamrole")
    suspend fun listTeamRole(): Response<ResultsResponse<MemberTaskItem>>

    @GET("master/gift")
    suspend fun listGift(): Response<ResultsResponse<GiftItem>>

    @GET("master/statusproposal")
    suspend fun listStatusProposal(): Response<ResultsResponse<StatusProposalItem>>
}