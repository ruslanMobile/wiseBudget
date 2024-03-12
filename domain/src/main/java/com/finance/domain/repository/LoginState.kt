package com.finance.domain.repository


sealed class LoginState {
    object DefaultLogin : LoginState()
    object SuccessLogin : LoginState()
    class LoadingLogin :LoginState()
    class FailLogin(val message: String?) : LoginState()

    class EmailIncorrect : LoginState()

    class PasswordIncorrect(val message: Int?) : LoginState()
}