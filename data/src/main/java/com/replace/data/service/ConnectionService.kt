package com.replace.data.service

import com.replace.data.remote.CustomResult
import retrofit2.http.GET

interface ConnectionService {

    @GET("/connection/code")
    fun getConnectionCode(): CustomResult<String>
}
