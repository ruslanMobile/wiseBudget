package com.finance.presentation.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T : Task<*>> Flow<T>.subscribeEvent(event: ((T) -> Unit)? = null, eventError: ((T) -> Unit)? = null, lifecycleScope: LifecycleCoroutineScope) {
    onEach { it.checkResponseServer(event,eventError) }.launchIn(lifecycleScope)
}

fun <T : Task<*>> T.checkResponseServer(event: ((T) -> Unit)? = null,eventError: ((T) -> Unit)? = null) {
    if (isSuccessful) {
        event?.invoke(this)
    } else {
        eventError?.invoke(this)
    }
}

fun <T> Flow<T>.requestNotCheckError(lifecycle:Lifecycle, lifecycleScope: LifecycleCoroutineScope, event: ((T) -> Unit)? = null) {
    flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
        .onEach { event?.invoke(it) }.launchIn(lifecycleScope)
}

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailRegex.matches(email)
}

fun calculateExpensePercentage(income: Int, expenses: Int): Double {
    return if (income != 0) {
        (expenses.toDouble() / income.toDouble()) * 100
    } else if (expenses != 0) {
        100.0
    } else 0.0
}