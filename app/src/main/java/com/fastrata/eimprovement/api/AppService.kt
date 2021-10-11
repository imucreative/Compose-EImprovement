package com.fastrata.eimprovement.api

import com.fastrata.eimprovement.features.login.data.model.LoginEntity
import com.fastrata.eimprovement.features.login.data.model.LoginRemoteRequest
import com.fastrata.eimprovement.ui.model.CategoryImprovementItem
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
}