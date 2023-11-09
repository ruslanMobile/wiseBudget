package com.finance.data.repository

import com.finance.data.R
import com.finance.domain.DEFAULT_EXPENSES_CATEGORY_FAMILY
import com.finance.domain.DEFAULT_EXPENSES_CATEGORY_HEALTH
import com.finance.domain.DEFAULT_EXPENSES_CATEGORY_TRANSPORT
import com.finance.domain.model.Category
import com.finance.domain.repository.BudgetCategoryRepository
import javax.inject.Inject

class BudgetCategoryRepositoryImpl  @Inject constructor(

) : BudgetCategoryRepository {

    override fun getListOfExpensesCategory(): List<Category> {
        return expensesCategories
    }
}

val expensesCategories = listOf(
    Category(
        name = DEFAULT_EXPENSES_CATEGORY_HEALTH,
        icon = R.drawable.ic_health
    ),
    Category(
        name = DEFAULT_EXPENSES_CATEGORY_FAMILY,
        icon = R.drawable.ic_family
    ),
    Category(
        name = DEFAULT_EXPENSES_CATEGORY_TRANSPORT,
        icon = R.drawable.ic_transport
    )
)