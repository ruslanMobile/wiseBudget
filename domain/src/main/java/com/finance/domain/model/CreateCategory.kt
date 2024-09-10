package com.finance.domain.model

data class CreateCategory(
    override val name: String,
    override val icon: Int,
    override var list: List<TransactionBase>? = null
): Category (name,icon, list)
