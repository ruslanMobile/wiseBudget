package com.finance.domain.usecase

import com.finance.domain.model.Expense
import com.finance.domain.model.Income
import com.finance.domain.repository.IncomeLogState
import com.finance.domain.repository.IncomesRepository
import javax.inject.Inject

class IncomesUseCase @Inject constructor(
    private val incomesRepository: IncomesRepository
){

    fun addIncomeToDb(model: Income, callback:(IncomeLogState) -> Unit) = incomesRepository.addIncomeToDb(model, callback)

    fun getAllIncomesFromDb(callback: (List<Income>?) -> Unit) = incomesRepository.getAllIncomesFromDb(callback)
}