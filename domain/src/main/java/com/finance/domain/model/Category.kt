package com.finance.domain.model

open class Category(
    open val name: String,
    open val icon: Int,
    open var list: List<TransactionBase>? = null
)
