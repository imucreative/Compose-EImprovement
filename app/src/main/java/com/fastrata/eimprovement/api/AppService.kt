package com.fastrata.eimprovement.api

import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.features.changespoint.data.model.GiftItem
import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.login.data.model.LoginRemoteRequest
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
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

    @GET("master/teammember/{DEPARTMENT_ID}/{ORG_ID}/{WAREHOUSE_ID}")
    suspend fun listTeamMember(
        @Path("DEPARTMENT_ID") departmentId: Int,
        @Path("ORG_ID") orgId: Int,
        @Path("WAREHOUSE_ID") warehouseId: Int,
    ): Response<ResultsResponse<MemberNameItem>>

    @GET("master/department/{DEPARTMENT_ID}/{ORG_ID}/{WAREHOUSE_ID}/{PROPOSAL_TYPE}")
    suspend fun listDepartment(
        @Path("DEPARTMENT_ID") departmentId: Int,
        @Path("ORG_ID") orgId: Int,
        @Path("WAREHOUSE_ID") warehouseId: Int,
        @Path("PROPOSAL_TYPE") proposalType: String
    ): Response<ResultsResponse<MemberDepartmentItem>>

    @GET("master/teamrole")
    suspend fun listTeamRole(): Response<ResultsResponse<MemberTaskItem>>

    @GET("master/gift")
    suspend fun listGift(): Response<ResultsResponse<GiftItem>>

    @GET("master/statusproposal")
    suspend fun listStatusProposal(): Response<ResultsResponse<StatusProposalItem>>

    @GET("master/branch")
    suspend fun listBranch(): Response<ResultsResponse<BranchItem>>

    @GET("master/subbranch/{ORG_ID}")
    suspend fun listSubBranch(@Path("ORG_ID") orgId: Int): Response<ResultsResponse<SubBranchItem>>

    @FormUrlEncoded
    @POST("ss/list")
    suspend fun listSs(@Field("userId") userId: Int): Response<ResultsResponse<SuggestionSystemModel>>

    @FormUrlEncoded
    @POST("pi/list")
    suspend fun listPi(@Field("userId") userId: Int): Response<ResultsResponse<ProjectImprovementModel>>

    @FormUrlEncoded
    @POST("rp/list")
    suspend fun listCp(@Field("userId") userId: Int): Response<ResultsResponse<ChangePointModel>>

    @FormUrlEncoded
    @POST("ss/detail")
    suspend fun detailSs(
        @Field("id") id: Int,
        @Field("userId") userId: Int
    ): Response<ResultsResponse<SuggestionSystemCreateModel>>

    @POST("appr/list")
    suspend fun listApproval(): Response<ResultsResponse<ApprovalModel>>
}
