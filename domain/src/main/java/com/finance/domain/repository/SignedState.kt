package com.finance.domain.repository


sealed class SignedState {
    object DefaultSignUp : SignedState()
    object SuccessSignUp : SignedState()
    class FailSignUp(val message: String?) : SignedState()
}