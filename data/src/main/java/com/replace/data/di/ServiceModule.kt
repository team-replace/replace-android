package com.replace.data.di

import com.replace.data.service.ConnectionService
import com.replace.data.service.DiaryService
import com.replace.data.service.MapService
import com.replace.data.service.ProfileService
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

    @Singleton
    @Provides
    fun provideProfileService(
        retrofit: Retrofit,
    ): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Singleton
    @Provides
    fun provideMapService(
        retrofit: Retrofit,
    ): MapService {
        return retrofit.create(MapService::class.java)
    }
}
