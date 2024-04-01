package com.finance.wisebudget

import android.util.Log
import com.finance.domain.repository.BudgetCategoryRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Rule
import org.junit.Test

class BudgetCategoryTest {

    @RelaxedMockK
    lateinit var categoryRepository: BudgetCategoryRepository

    @get:Rule
    val rule = MockKRule(this)

    @Test
    fun textListOfCategory(){
       // categoryRepository.getListOfExpensesCategory()
        every { categoryRepository.getListOfExpensesCategory() } answers {
            Log.e("MyLog","getListOfExpensesCategory called")
            System.out.println("Hello StackOverflow")
            listOf()
        }
    }
}