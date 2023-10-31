package com.replace.data.remote

import ReplaceCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ReplaceCallAdapter<T>(
    private val successType: Type,
) : CallAdapter<T, Call<CustomResult<T>>> {
    override fun responseType(): Type = successType
    override fun adapt(call: Call<T>): Call<CustomResult<T>> = ReplaceCall(call)
}
