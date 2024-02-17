package com.finance.presentation.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.model.Category
import com.finance.domain.model.Expense
import com.finance.domain.model.Income
import com.finance.domain.repository.ExpenseLogState
import com.finance.domain.repository.IncomeLogState
import com.finance.domain.usecase.BudgetCategoryUseCase
import com.finance.domain.usecase.ExpensesUseCase
import com.finance.domain.usecase.IncomesUseCase
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
    private val incomesUseCase: IncomesUseCase
) : ViewModel() {

    val expenses = savedStateHandle.getStateFlow("expenses", mutableListOf<Category>())
    val incomes = savedStateHandle.getStateFlow("incomes", mutableListOf<Category>())

    private var expenseLogState =
        MutableSharedFlow<ExpenseLogState>()
    val _expenseLogState = expenseLogState.asSharedFlow()

    private var incomeLogState =
        MutableSharedFlow<IncomeLogState>()
    val _incomeLogState = incomeLogState.asSharedFlow()

    init {
        getListOfExpensesCategory()
        getListOfIncomesCategory()
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

    fun getListOfIncomesCategory() = viewModelScope.launch {
        val categories = categoryUseCase.getListOfIncomesCategory()
        incomesUseCase.getAllIncomesFromDb { incomes ->
            categories.forEach { category ->
                category.list = incomes?.filter { it.category == category.name }
            }
            savedStateHandle["incomes"] = categories
        }
    }

    fun addExpenseToDb(expense: Expense) {
        expensesUseCase.addExpenseToDb(expense) { result ->
            viewModelScope.launch {
                expenseLogState.emit(result)
            }
        }
    }

    fun addIncomeToDb(income: Income) {
        incomesUseCase.addIncomeToDb(income) { result ->
            viewModelScope.launch {
                incomeLogState.emit(result)
            }
        }
    }
}