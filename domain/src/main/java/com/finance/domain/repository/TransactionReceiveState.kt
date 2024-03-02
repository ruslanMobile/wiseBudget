package com.finance.domain.repository

import com.finance.domain.model.TransactionBase

sealed class TransactionReceiveState {

    object Initial : TransactionReceiveState()
    object Loading : TransactionReceiveState()

    class Error(val message: String) : TransactionReceiveState()

    class Success(val list: List<TransactionBase>) : TransactionReceiveState()
}