package com.finance.domain.usecase

import com.finance.domain.repository.LoginState
import com.finance.domain.repository.EmailAuthRepository
import com.finance.domain.repository.SignedState
import javax.inject.Inject

class EmailAuthUseCase @Inject constructor(
    private val emailAuthRepository: EmailAuthRepository
) {

    suspend fun signUpWithEmail(email: String, password: String, func:(SignedState) -> Unit) =
        emailAuthRepository.signUpWithEmail(email, password, func)

    suspend fun loginWithEmail(email: String, password: String, func:(LoginState) -> Unit) =
        emailAuthRepository.loginWithEmail(email, password, func)
}