package com.finance.domain.repository


interface EmailAuthRepository {
    suspend fun signUpWithEmail(email: String, password: String, func: (SignedState) -> Unit)
    suspend fun loginWithEmail(email: String, password: String, func: (LoginState) -> Unit)
}