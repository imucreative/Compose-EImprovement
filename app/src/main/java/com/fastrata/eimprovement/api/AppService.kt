package com.fastrata.eimprovement.api

import com.fastrata.eimprovement.features.approval.data.model.ApprovalModel
import com.fastrata.eimprovement.features.approval.data.model.ApprovalRemoteRequest
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointCreateItemModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointModel
import com.fastrata.eimprovement.features.changespoint.data.model.ChangePointRemoteRequest
import com.fastrata.eimprovement.features.changespoint.data.model.GiftItem
import com.fastrata.eimprovement.features.dashboard.ui.data.BalanceModel
import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.login.data.model.LoginRemoteRequest
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementModel
import com.fastrata.eimprovement.features.projectimprovement.data.model.ProjectImprovementRemoteRequest
import com.fastrata.eimprovement.features.settings.ui.changepassword.data.model.ChangePasswordModel
import com.fastrata.eimprovement.features.settings.ui.mutasi.data.model.MutasiModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemRemoteRequest
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemCreateModel
import com.fastrata.eimprovement.features.suggestionsystem.data.model.SuggestionSystemResponseModel
import com.fastrata.eimprovement.featuresglobal.data.model.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

/**
 * App REST API access points
 */
interface AppService {
    // Login
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRemoteRequest): Response<ResultsResponse<LoginEntity>>

    // Master
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

    @GET("transaction/checkperiod/{TYPE_PROPOSAL}")
    suspend fun checkPeriod(@Path("TYPE_PROPOSAL") typeProposal: String): Response<ResultsResponse<StatusProposalItem>>


    // Suggestion System
    @POST("ss/list")
    suspend fun listSs(
        @Body listSuggestionSystemRemoteRequest: SuggestionSystemRemoteRequest
    ): Response<ResultsResponse<SuggestionSystemModel>>

    @FormUrlEncoded
    @POST("ss/detail")
    suspend fun detailSs(
        @Field("id") id: Int,
        @Field("userId") userId: Int
    ): Response<ResultsResponse<SuggestionSystemCreateModel>>

    @POST("ss/create")
    suspend fun submitCreateSs(
        @Body suggestionSystemCreateModel: SuggestionSystemCreateModel
    ): Response<ResultsResponse<SuggestionSystemResponseModel>>


    // Project Improvement
    @POST("pi/list")
    suspend fun listPi(
        @Body listProjectImprovementRemoteRequest: ProjectImprovementRemoteRequest
    ): Response<ResultsResponse<ProjectImprovementModel>>

    @FormUrlEncoded
    @POST("pi/detail")
    suspend fun detailPi(
        @Field("id") id: Int,
        @Field("userId") userId: Int
    ): Response<ResultsResponse<ProjectImprovementCreateModel>>


    // Reward Point
    @POST("rp/list")
    suspend fun listCp(
        @Body listChangePointRemoteRequest: ChangePointRemoteRequest
    ): Response<ResultsResponse<ChangePointModel>>

    @FormUrlEncoded
    @POST("rp/detail")
    suspend fun detailCp(
        @Field("id") id: Int,
        @Field("userId") userId: Int
    ): Response<ResultsResponse<ChangePointCreateItemModel>>


    // Approval
    @POST("appr/list")
    suspend fun listApproval(
        @Body listApprovalRemoteRequest: ApprovalRemoteRequest
    ): Response<ResultsResponse<ApprovalModel>>

    @FormUrlEncoded
    @POST("saldo/balance")
    suspend fun getBalance(
        @Field("userId") id: Int
    ): Response<ResultsResponse<BalanceModel>>

    @FormUrlEncoded
    @POST("saldo/detail")
    suspend fun getBalancedetail(
        @Field("userId") id: Int,
        @Field("limit") limit:Int,
        @Field("page") page: Int
    ): Response<ResultsResponse<MutasiModel>>

    @FormUrlEncoded
    @POST("auth/changepassword")
    suspend fun changePassword(
        @Field("user_id") id: Int,
        @Field("user_name") userName: String,
        @Field("user_password") userPassword: String,
        @Field("new_password") newPassword: String
    ): Response<ResultsResponse<ChangePasswordModel>>

    @Multipart
    @POST("transaction/upload-files/{type}/{group}/{createdBy}")
    suspend fun uploadAttachment(
        @Part file: MultipartBody.Part,
        @Path("type") type: String,
        @Path("group") group: String,
        @Path("createdBy") createdBy: String
    ): Response<ResultsResponse<AttachmentItem>>

    @DELETE("transaction/deletefiles")
    suspend fun removeAttachment(
        @Query("attach_id") attachmentId: Int,
        @Query("file_name") fileName: String,
        @Query("type") type: String
    ): Response<ResultsResponse<String>>
}
