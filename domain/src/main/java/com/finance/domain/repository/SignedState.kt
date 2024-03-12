package com.finance.domain.repository


sealed class SignedState {
    object DefaultSignUp : SignedState()
    object SuccessSignUp : SignedState()
    class LoadingSignUp : SignedState()
    class FailSignUp(val message: String?) : SignedState()
    class EmailIncorrect : SignedState()
    class PasswordIncorrect() : SignedState()
    class RepeatPasswordIncorrect() : SignedState()
}