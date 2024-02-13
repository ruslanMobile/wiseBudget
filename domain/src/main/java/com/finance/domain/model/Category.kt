package com.finance.domain.model

data class Category(
    val name: String,
    val icon: Int,
    var list: List<Expense>? = null
)
