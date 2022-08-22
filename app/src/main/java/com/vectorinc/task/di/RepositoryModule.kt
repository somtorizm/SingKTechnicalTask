package com.vectorinc.task.di

import com.vectorinc.task.data.remote.repository.CharacterRepositoryImpl
import com.vectorinc.task.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        characterRepository: CharacterRepositoryImpl
    ): CharacterRepository


}