package com.replace.data.repository.profile

import com.replace.data.model.response.ProfileResponse
import com.replace.data.remote.CustomResult

interface ProfileRepository {

    suspend fun getProfile(): CustomResult<ProfileResponse>
}
