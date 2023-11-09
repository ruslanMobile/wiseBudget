package com.finance.presentation.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.model.Category
import com.finance.domain.model.Expense
import com.finance.domain.usecase.BudgetCategoryUseCase
import com.finance.domain.usecase.ExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val categoryUseCase: BudgetCategoryUseCase,
    private val expensesUseCase: ExpensesUseCase
) : ViewModel() {

    val expenses = savedStateHandle.getStateFlow("expenses", listOf<Category>())

    init {
        getListOfExpensesCategory()
    }

    fun getListOfExpensesCategory()= viewModelScope.launch{
        savedStateHandle["expenses"] = categoryUseCase.getListOfExpensesCategory()
    }

    fun addExpenseToDb() {
        expensesUseCase.addExpenseToDb(Expense("name","type", 463L)).onEach {  }.launchIn(viewModelScope)
    }
}