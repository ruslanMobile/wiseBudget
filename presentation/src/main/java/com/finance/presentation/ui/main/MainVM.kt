package com.finance.presentation.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.model.Category
import com.finance.domain.model.Expense
import com.finance.domain.usecase.BudgetCategoryUseCase
import com.finance.domain.usecase.ExpensesUseCase
import com.finance.domain.repository.ExpenseLogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val categoryUseCase: BudgetCategoryUseCase,
    private val expensesUseCase: ExpensesUseCase
) : ViewModel() {

    val expenses = savedStateHandle.getStateFlow("expenses", listOf<Category>())

    private var expenseLogState = MutableStateFlow<ExpenseLogState>(ExpenseLogState.InitExpenseLogState)
    val _expenseLogState = expenseLogState.asStateFlow()

    init {
        getListOfExpensesCategory()
    }

    fun getListOfExpensesCategory() = viewModelScope.launch {
        savedStateHandle["expenses"] = categoryUseCase.getListOfExpensesCategory()
    }

    fun addExpenseToDb(expense: Expense) {
        expensesUseCase.addExpenseToDb(expense){ result ->
            expenseLogState.value = result
        }
    }
}