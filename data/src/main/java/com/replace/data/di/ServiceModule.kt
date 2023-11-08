package com.replace.data.di

import com.replace.data.service.ConnectionService
import com.replace.data.service.DiaryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideDiaryService(
        retrofit: Retrofit,
    ): DiaryService {
        return retrofit.create(DiaryService::class.java)
    }

    @Singleton
    @Provides
    fun provideConnectionService(
        retrofit: Retrofit,
    ): ConnectionService {
        return retrofit.create(ConnectionService::class.java)
    }
}
