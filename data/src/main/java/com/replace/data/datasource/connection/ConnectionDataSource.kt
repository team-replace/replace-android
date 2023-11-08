package com.replace.data.datasource.connection

import com.replace.data.remote.CustomResult

interface ConnectionDataSource {

    suspend fun getConnectionCode(): CustomResult<String>
}
