package com.finance.domain.usecase

import com.finance.domain.model.Expense
import com.finance.domain.repository.ExpensesRepository
import javax.inject.Inject

class ExpensesUseCase @Inject constructor(
    private val expensesRepository: ExpensesRepository
) {

    fun addExpenseToDb(model: Expense) = expensesRepository.addExpenseToDb(model)
}