package com.replace.data.remote

import com.replace.data.common.CustomThrowable
import com.replace.data.common.FetchState
import okhttp3.Headers

sealed class CustomResult<T> {

    class Success<T>(val data: T, val headers: Headers) : CustomResult<T>()
    class ApiError<T>(val customThrowable: CustomThrowable) : CustomResult<T>()
    class UnKnownApiError<T>(val code: Int) : CustomResult<T>()
    class NetworkError<T>(val fetchState: FetchState) : CustomResult<T>()
    class NullCustomResult<T> : CustomResult<T>()
}
