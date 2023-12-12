package com.replace.data.repository.connection

import com.replace.data.model.response.ConnectionCodeResponse
import com.replace.data.remote.CustomResult

interface ConnectionRepository {

    suspend fun getConnectionCode(): CustomResult<ConnectionCodeResponse>
    suspend fun postConnectionCode(code: String): CustomResult<Unit>
}
