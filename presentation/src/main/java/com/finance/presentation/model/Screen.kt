package com.finance.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Screen(val route: String) : Parcelable {

    @Parcelize
    object LoginOrSignUp : Screen("loginOrSignup")
    @Parcelize
    object Login : Screen("login")
    @Parcelize
    object SignUp : Screen("sign_up")
    @Parcelize
    object Main : Screen("main")
    @Parcelize
    object Categories : Screen("categories")
    @Parcelize
    object Profile : Screen("profile")
    @Parcelize
    object ExpensesIncomes : Screen("expenses_incomes_list")
    @Parcelize
    object ExpensesLog : Screen("expense_log")
    @Parcelize
    object IncomeLog : Screen("income_log")
    @Parcelize
    object CreateCategory : Screen("create_category")
    @Parcelize
    object ChooseCategoryIcon : Screen("choose_category_icon")
}