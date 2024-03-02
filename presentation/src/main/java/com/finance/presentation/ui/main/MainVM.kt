package com.finance.presentation.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.model.Category
import com.finance.domain.model.Expense
import com.finance.domain.model.Income
import com.finance.domain.repository.ExpenseLogState
import com.finance.domain.repository.IncomeLogState
import com.finance.domain.repository.TransactionReceiveState
import com.finance.domain.usecase.BudgetCategoryUseCase
import com.finance.domain.usecase.ExpensesUseCase
import com.finance.domain.usecase.IncomesUseCase
import com.finance.presentation.utils.getEndTimeFromParticularMonth
import com.finance.presentation.utils.getStartTimeFromParticularMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val categoryUseCase: BudgetCategoryUseCase,
    private val expensesUseCase: ExpensesUseCase,
    private val incomesUseCase: IncomesUseCase
) : ViewModel() {

    val selectedDateRange =
        savedStateHandle.getStateFlow("selectedDateRange", Calendar.getInstance().time.time)
    val expensesOrIncomesSelected = savedStateHandle.getStateFlow("expensesOrIncomesSelected", 0)

    private var expenseLogState =
        MutableSharedFlow<ExpenseLogState>()
    val _expenseLogState = expenseLogState.asSharedFlow()

    private var incomeLogState =
        MutableSharedFlow<IncomeLogState>()
    val _incomeLogState = incomeLogState.asSharedFlow()

    private var incomesReceiveState =
        MutableStateFlow<TransitionUIState>(TransitionUIState.Initial)
    val _incomesReceiveState = incomesReceiveState.asStateFlow()

    private var expensesReceiveState =
        MutableStateFlow<TransitionUIState>(TransitionUIState.Initial)
    val _expensesReceiveState = expensesReceiveState.asStateFlow()

    init {
        getListOfExpensesCategory()
        getListOfIncomesCategory()
    }

    private fun getListOfExpensesCategory() = viewModelScope.launch {
        val categories = categoryUseCase.getListOfExpensesCategory()
        expensesUseCase.getAllExpensesFromDb(
            getStartTimeFromParticularMonth(selectedDateRange.value),
            getEndTimeFromParticularMonth(selectedDateRange.value)
        ) { expensesState ->
            viewModelScope.launch {
                when (expensesState) {
                    is TransactionReceiveState.Success -> {
                        categories.forEach { category ->
                            category.list =
                                expensesState.list.filter { it.category == category.name }
                        }
                        expensesReceiveState.emit(TransitionUIState.Success(categories))
                    }

                    is TransactionReceiveState.Loading -> {
                        expensesReceiveState.emit(
                            TransitionUIState.Loading
                        )
                    }

                    is TransactionReceiveState.Error -> {
                        expensesReceiveState.emit(
                            TransitionUIState.Error(
                                expensesState.message
                            )
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getListOfIncomesCategory() = viewModelScope.launch {
        val categories = categoryUseCase.getListOfIncomesCategory()
        incomesUseCase.getAllIncomesFromDb(
            getStartTimeFromParticularMonth(selectedDateRange.value),
            getEndTimeFromParticularMonth(selectedDateRange.value)
        ) { incomesState ->
            viewModelScope.launch {
                when (incomesState) {
                    is TransactionReceiveState.Success -> {
                        categories.forEach { category ->
                            category.list =
                                incomesState.list.filter { it.category == category.name }
                        }
                        incomesReceiveState.emit(TransitionUIState.Success(categories))
                    }

                    is TransactionReceiveState.Loading -> incomesReceiveState.emit(TransitionUIState.Loading)

                    is TransactionReceiveState.Error -> incomesReceiveState.emit(
                        TransitionUIState.Error(
                            incomesState.message
                        )
                    )

                    else -> {}
                }
            }
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

    fun changeSelectedMonth(diff: Int) {
        val newDate = Calendar.getInstance().apply {
            time = Date(selectedDateRange.value)
            add(Calendar.MONTH, diff)
        }
        savedStateHandle["selectedDateRange"] = newDate.time.time
        getListOfIncomesCategory()
        getListOfExpensesCategory()
    }

    fun setIncomeOrExpenseState(selected: Int) {
        savedStateHandle["expensesOrIncomesSelected"] = selected
    }
}

sealed class TransitionUIState {
    object Initial : TransitionUIState()
    object Loading : TransitionUIState()

    class Error(val message: String) : TransitionUIState()

    class Success(val list: List<Category>) : TransitionUIState()
}