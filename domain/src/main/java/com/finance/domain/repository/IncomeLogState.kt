package com.finance.domain.repository

sealed class IncomeLogState {
    object InitIncomeLogState : IncomeLogState()
    object SuccessIncomeLogState : IncomeLogState()
    class FailureIncomeLogState(val message:String?) : IncomeLogState()
}