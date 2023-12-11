package com.replace.data.datasource.profile

import com.replace.data.model.response.ProfileResponse
import com.replace.data.remote.CustomResult

interface ProfileDataSource {

    suspend fun getProfile(): CustomResult<ProfileResponse>
}
