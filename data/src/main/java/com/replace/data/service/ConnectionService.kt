package com.replace.data.service

import com.replace.data.model.response.ConnectionCodeResponse
import com.replace.data.remote.CustomResult
import retrofit2.http.GET

interface ConnectionService {

    @GET("/connection/code")
    suspend fun getConnectionCode(): CustomResult<ConnectionCodeResponse>
}
