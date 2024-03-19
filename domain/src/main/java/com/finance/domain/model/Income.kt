package com.finance.domain.model

data class Income(
    val incomeName: String?,
    override val category: String?,
    override val amount: Int?,
    override val dateLong: Long?,
): TransactionBase(){

    override fun isNullOrBlankName(): Boolean {
        return incomeName.isNullOrBlank()
    }
}
