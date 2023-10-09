package com.finance.data.di

import com.finance.data.repository.EmailAuthRepositoryImpl
import com.finance.data.repository.GoogleAuthRepositoryImpl
import com.finance.domain.repository.EmailAuthRepository
import com.finance.domain.repository.GoogleAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindUEmailAuthRepository(impl: EmailAuthRepositoryImpl): EmailAuthRepository

    @Binds
    abstract fun bindGoogleAuthRepository(impl: GoogleAuthRepositoryImpl): GoogleAuthRepository
}