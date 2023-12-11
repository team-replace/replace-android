package com.replace.data.remote

import com.replace.data.common.CustomThrowable
import org.json.JSONObject
import retrofit2.Response

fun <T> createCustomThrowableFromResponse(result: Response<T>): CustomThrowable {
    val errorResponse = result.errorBody()?.string()
    val json = errorResponse?.let { JSONObject(it) }
    val errorMessage = json?.getString("errorBody") ?: ""
    val errorCode = json?.getInt("errorCode") ?: 0
    return CustomThrowable(errorCode, errorMessage)
}
