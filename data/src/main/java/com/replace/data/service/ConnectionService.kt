package com.replace.data.service

import com.replace.data.model.request.ConnectionCodeRequest
import com.replace.data.remote.CustomResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ConnectionService {

    @GET("/connection/code")
    suspend fun getConnectionCode(): CustomResult<String>

    @POST("/connection/code")
    suspend fun postConnectionCode(
        @Body connectionCodeRequest: ConnectionCodeRequest,
    ): CustomResult<Unit>
}
