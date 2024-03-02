package com.finance.domain.usecase

import com.finance.domain.model.Expense
import com.finance.domain.repository.ExpenseLogState
import com.finance.domain.repository.ExpensesRepository
import com.finance.domain.repository.TransactionReceiveState
import javax.inject.Inject

class ExpensesUseCase @Inject constructor(
    private val expensesRepository: ExpensesRepository
) {

    fun addExpenseToDb(model: Expense, callback:(ExpenseLogState) -> Unit) = expensesRepository.addExpenseToDb(model, callback)

    fun getAllExpensesFromDb(startDate: Long, endDate: Long, callback: (TransactionReceiveState) -> Unit) = expensesRepository.getAllExpensesFromDb(startDate, endDate, callback)
}