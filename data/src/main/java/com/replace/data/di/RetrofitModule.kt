package com.replace.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.replace.data.remote.ReplaceCallAdapterFactory
import com.replace.data.service.client.AccessTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL = "http://54.153.140.131:8080"
    private val CONTENT_TYPE = "application/json".toMediaType()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(ReplaceCallAdapterFactory())
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(CONTENT_TYPE))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .run {
                addInterceptor(AccessTokenInterceptor)
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    },
                )
                build()
            }
    }
}
