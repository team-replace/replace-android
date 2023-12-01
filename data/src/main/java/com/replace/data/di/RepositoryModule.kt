package com.replace.data.di

import com.replace.data.repository.connection.ConnectionRepository
import com.replace.data.repository.connection.DefaultConnectionRepository
import com.replace.data.repository.diary.DefaultDiaryRepository
import com.replace.data.repository.diary.DiaryRepository
import com.replace.data.repository.map.DefaultMapRepository
import com.replace.data.repository.map.MapRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDiaryRepository(
        repository: DefaultDiaryRepository,
    ): DiaryRepository

    @Singleton
    @Binds
    abstract fun bindConnectionRepository(
        repository: DefaultConnectionRepository,
    ): ConnectionRepository

    @Singleton
    @Binds
    abstract fun bindMapRepository(
        repository: DefaultMapRepository,
    ): MapRepository
}
