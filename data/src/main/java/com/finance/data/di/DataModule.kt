package com.finance.data.di

import com.finance.data.repository.BudgetCategoryRepositoryImpl
import com.finance.data.repository.EmailAuthRepositoryImpl
import com.finance.data.repository.ExpensesRepositoryImpl
import com.finance.data.repository.GoogleAuthRepositoryImpl
import com.finance.data.repository.IncomesRepositoryImpl
import com.finance.domain.repository.BudgetCategoryRepository
import com.finance.domain.repository.EmailAuthRepository
import com.finance.domain.repository.ExpensesRepository
import com.finance.domain.repository.GoogleAuthRepository
import com.finance.domain.repository.IncomesRepository
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

    @Binds
    abstract fun bindBudgetCategoryRepository(impl: BudgetCategoryRepositoryImpl): BudgetCategoryRepository

    @Binds
    abstract fun bindExpensesRepository(impl: ExpensesRepositoryImpl): ExpensesRepository

    @Binds
    abstract fun bindIncomesRepository(impl: IncomesRepositoryImpl): IncomesRepository
}