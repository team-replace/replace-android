package com.replace.data.repository.connection

import com.replace.data.datasource.connection.ConnectionDataSource
import com.replace.data.model.response.ConnectionCodeResponse
import com.replace.data.remote.CustomResult
import javax.inject.Inject

class DefaultConnectionRepository @Inject constructor(
    private val connectionDataSource: ConnectionDataSource,
) : ConnectionRepository {
    override suspend fun getConnectionCode(): CustomResult<ConnectionCodeResponse> {
        return connectionDataSource.getConnectionCode()
    }
}
