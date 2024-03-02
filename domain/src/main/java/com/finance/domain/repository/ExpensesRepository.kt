package com.finance.domain.repository

import com.finance.domain.model.Expense

interface ExpensesRepository {
    fun addExpenseToDb(model: Expense, callback:(ExpenseLogState) -> Unit)

    fun getAllExpensesFromDb(startDate: Long, endDate: Long, callback: (TransactionReceiveState) -> Unit)
}