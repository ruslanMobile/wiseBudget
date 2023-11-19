package com.finance.domain.model

data class Expense(
    val expenseName: String,
    val category: String,
    val amount: Int,
    val dateLong: Long,
)
