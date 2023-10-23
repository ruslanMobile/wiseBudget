package com.finance.domain.usecase

import com.finance.domain.repository.LoginState
import com.finance.domain.repository.EmailAuthRepository
import com.finance.domain.repository.GoogleAuthRepository
import com.finance.domain.repository.SignedState
import javax.inject.Inject

class GoogleAuthUseCase @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository
) {

    fun authenticationWithGoogle(idToken: String?, resFunc: (LoginState) -> Unit) =
        googleAuthRepository.authenticationWithGoogle(idToken, resFunc)

    fun alreadyLoggedIn() = googleAuthRepository.alreadyLoggedIn()
}