package com.finance.domain.repository


sealed class LoginState {
    object DefaultLogin : LoginState()
    object SuccessLogin : LoginState()
    class FailLogin(val message: String?) : LoginState()
}