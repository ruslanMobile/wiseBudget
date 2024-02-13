package com.finance.presentation.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.model.Category
import com.finance.domain.model.Expense
import com.finance.domain.repository.ExpenseLogState
import com.finance.domain.usecase.BudgetCategoryUseCase
import com.finance.domain.usecase.ExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val categoryUseCase: BudgetCategoryUseCase,
    private val expensesUseCase: ExpensesUseCase,
) : ViewModel() {

    val expenses = savedStateHandle.getStateFlow("expenses", mutableListOf<Category>())

    private var expenseLogState =
        MutableSharedFlow<ExpenseLogState>()
    val _expenseLogState = expenseLogState.asSharedFlow()

    init {
        getListOfExpensesCategory()
    }

    fun getListOfExpensesCategory() = viewModelScope.launch {
        val categories = categoryUseCase.getListOfExpensesCategory()
        expensesUseCase.getAllExpensesFromDb { expenses ->
            categories.forEach { category ->
                category.list = expenses?.filter { it.category == category.name }
            }
            savedStateHandle["expenses"] = categories
        }
    }

    fun addExpenseToDb(expense: Expense) {
        expensesUseCase.addExpenseToDb(expense) { result ->
            viewModelScope.launch {
                expenseLogState.emit(result)
            }
        }
    }
}