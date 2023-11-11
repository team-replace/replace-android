package com.replace.data.datasource.connection

import com.replace.data.model.response.ConnectionCodeResponse
import com.replace.data.remote.CustomResult
import com.replace.data.service.ConnectionService
import javax.inject.Inject

class ConnectionRemoteDataSource @Inject constructor(
    private val connectionService: ConnectionService,
) : ConnectionDataSource {
    override suspend fun getConnectionCode(): CustomResult<ConnectionCodeResponse> {
        return connectionService.getConnectionCode()
    }
}
