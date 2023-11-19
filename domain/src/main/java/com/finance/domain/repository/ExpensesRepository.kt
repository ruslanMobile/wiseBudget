package com.finance.domain.repository

import com.finance.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpensesRepository {
    fun addExpenseToDb(model: Expense, callback:(ExpenseLogState) -> Unit)
}