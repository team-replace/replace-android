package com.replace.data.service.client

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

object AccessTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest: Request =
            request().newBuilder()
                .addHeader("temporary", "pobi")
                .build()

        proceed(newRequest)
    }
}
