package com.finance.domain.repository

import com.finance.domain.model.Category

interface BudgetCategoryRepository {
    fun getListOfExpensesCategory(): List<Category>
    fun getListOfIncomesCategory(): List<Category>
}