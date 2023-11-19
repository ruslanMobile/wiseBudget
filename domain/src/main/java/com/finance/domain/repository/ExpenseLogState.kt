package com.finance.domain.repository

sealed class ExpenseLogState {
    object InitExpenseLogState : ExpenseLogState()
    object SuccessExpenseLogState : ExpenseLogState()
    class FailureExpenseLogState(val message:String?) : ExpenseLogState()
}