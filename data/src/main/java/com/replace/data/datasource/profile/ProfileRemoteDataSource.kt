package com.replace.data.datasource.profile

import com.replace.data.model.response.ProfileResponse
import com.replace.data.remote.CustomResult
import com.replace.data.service.ProfileService
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val profileService: ProfileService,
) : ProfileDataSource {
    override suspend fun getProfile(): CustomResult<ProfileResponse> {
        return profileService.getProfile()
    }
}
