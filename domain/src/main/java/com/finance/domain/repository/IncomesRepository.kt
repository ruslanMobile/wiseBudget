package com.finance.domain.repository

import com.finance.domain.model.Income

interface IncomesRepository {
    fun addIncomeToDb(model: Income, callback: (IncomeLogState) -> Unit)

    fun getAllIncomesFromDb(callback: (List<Income>?) -> Unit)
}