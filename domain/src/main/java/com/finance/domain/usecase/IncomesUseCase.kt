package com.finance.domain.usecase

import com.finance.domain.model.Expense
import com.finance.domain.model.Income
import com.finance.domain.repository.IncomeLogState
import com.finance.domain.repository.IncomesRepository
import com.finance.domain.repository.TransactionReceiveState
import javax.inject.Inject

class IncomesUseCase @Inject constructor(
    private val incomesRepository: IncomesRepository
){

    fun addIncomeToDb(model: Income, callback:(IncomeLogState) -> Unit) = incomesRepository.addIncomeToDb(model, callback)

    fun getAllIncomesFromDb(startDate: Long, endDate: Long, callback: (TransactionReceiveState) -> Unit) = incomesRepository.getAllIncomesFromDb(startDate, endDate, callback)
}