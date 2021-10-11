package com.fastrata.eimprovement.api

import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.login.data.model.LoginRemoteRequest
import com.fastrata.eimprovement.ui.model.CategoryImprovementItem
import com.fastrata.eimprovement.ui.model.MemberDepartmentItem
import com.fastrata.eimprovement.ui.model.MemberNameItem
import com.fastrata.eimprovement.ui.model.MemberTaskItem
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
}