package com.finance.domain.usecase

import com.finance.domain.repository.BudgetCategoryRepository
import javax.inject.Inject

class BudgetCategoryUseCase  @Inject constructor(
    private val categoryRepository: BudgetCategoryRepository
) {

    fun getListOfExpensesCategory() = categoryRepository.getListOfExpensesCategory()

    fun getListOfIncomesCategory() = categoryRepository.getListOfIncomesCategory()
}