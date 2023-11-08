package com.replace.data.datasource.connection

import com.replace.data.model.request.ConnectionCodeRequest
import com.replace.data.remote.CustomResult

interface ConnectionDataSource {

    suspend fun getConnectionCode(): CustomResult<String>

    suspend fun postConnectionCode(connectionCodeRequest: ConnectionCodeRequest): CustomResult<Unit>
}
