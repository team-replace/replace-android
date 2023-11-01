package com.replace.data.di

import com.replace.data.repository.diary.DefaultDiaryRepository
import com.replace.data.repository.diary.DiaryRepository
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
}
