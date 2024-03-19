package com.finance.domain.model

abstract class TransactionBase {
    abstract val category: String?
    abstract val amount: Int?
    abstract val dateLong: Long?

    abstract fun isNullOrBlankName(): Boolean
}