package com.replace.data.di

import com.replace.data.datasource.connection.ConnectionDataSource
import com.replace.data.datasource.connection.ConnectionRemoteDataSource
import com.replace.data.datasource.diary.DiaryDataSource
import com.replace.data.datasource.diary.DiaryRemoteDataSource
import com.replace.data.datasource.map.MapDataSource
import com.replace.data.datasource.map.MapRemoteDataSource
import com.replace.data.datasource.profile.ProfileDataSource
import com.replace.data.datasource.profile.ProfileRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindDiaryDataSource(
        remoteDataSource: DiaryRemoteDataSource,
    ): DiaryDataSource

    @Singleton
    @Binds
    abstract fun bindConnectionDataSource(
        remoteDataSource: ConnectionRemoteDataSource,
    ): ConnectionDataSource

    @Singleton
    @Binds
    abstract fun bindProfileDataSource(
        remoteDataSource: ProfileRemoteDataSource,
    ): ProfileDataSource

    @Singleton
    @Binds
    abstract fun bindMapDataSource(
        remoteDataSource: MapRemoteDataSource,
    ): MapDataSource
}
