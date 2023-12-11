package com.replace.data.repository.profile

import com.replace.data.datasource.profile.ProfileDataSource
import com.replace.data.model.response.ProfileResponse
import com.replace.data.remote.CustomResult
import javax.inject.Inject

class DefaultProfileRepository @Inject constructor(
    private val profileDataSource: ProfileDataSource,
) : ProfileRepository {
    override suspend fun getProfile(): CustomResult<ProfileResponse> {
        return profileDataSource.getProfile()
    }
}
