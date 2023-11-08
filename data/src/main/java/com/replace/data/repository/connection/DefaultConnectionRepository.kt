package com.replace.data.repository.connection

import com.replace.data.datasource.connection.ConnectionDataSource
import com.replace.data.model.request.ConnectionCodeRequest
import com.replace.data.remote.CustomResult
import javax.inject.Inject

class DefaultConnectionRepository @Inject constructor(
    private val connectionDataSource: ConnectionDataSource,
) : ConnectionRepository {
    override suspend fun getConnectionCode(): CustomResult<String> {
        return connectionDataSource.getConnectionCode()
    }

    override suspend fun postConnectionCode(code: String): CustomResult<Unit> {
        return connectionDataSource.postConnectionCode(ConnectionCodeRequest(code))
    }
}
