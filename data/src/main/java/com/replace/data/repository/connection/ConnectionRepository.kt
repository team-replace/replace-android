package com.replace.data.repository.connection

import com.replace.data.remote.CustomResult

interface ConnectionRepository {

    suspend fun getConnectionCode(): CustomResult<String>
}
