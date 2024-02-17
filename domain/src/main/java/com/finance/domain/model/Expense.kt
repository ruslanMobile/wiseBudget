package com.finance.domain.model

data class Expense(
    val expenseName: String?,
    override val category: String?,
    override val amount: Int?,
    override val dateLong: Long?,
): TransactionBase()
