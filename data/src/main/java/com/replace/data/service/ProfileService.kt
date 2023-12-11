package com.replace.data.service

import com.replace.data.model.response.ProfileResponse
import com.replace.data.remote.CustomResult
import retrofit2.http.GET

interface ProfileService {

    @GET("/my")
    suspend fun getProfile(): CustomResult<ProfileResponse>
}
