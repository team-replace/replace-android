package com.replace.data.di

import com.replace.data.datasource.connection.ConnectionDataSource
import com.replace.data.datasource.connection.ConnectionRemoteDataSource
import com.replace.data.datasource.diary.DiaryDataSource
import com.replace.data.datasource.diary.DiaryRemoteDataSource
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
}
