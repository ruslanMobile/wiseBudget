package com.finance.data.di

import com.finance.data.mapper.ExpenseDocumentSnapshotMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {

    @Singleton
    @Provides
    fun getExpenseMapper() = ExpenseDocumentSnapshotMapper()
}