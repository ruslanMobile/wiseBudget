package com.finance.domain.repository

interface GoogleAuthRepository {
    fun authenticationWithGoogle(idToken: String?, resFunc: (LoginState) -> Unit)
    fun alreadyLoggedIn(): Boolean
}