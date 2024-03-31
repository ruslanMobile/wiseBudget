package com.finance.domain.repository

sealed class LogoutState {

    object DefaultLogout : LogoutState()
    object SuccessLogout : LogoutState()
    class LoadingLogout :LogoutState()
    class FailLogout(val message: String?) : LogoutState()
}