package com.finance.domain.usecase

import com.finance.domain.repository.EmailAuthRepository
import javax.inject.Inject

class EmailAuthUseCase @Inject constructor(
    private val emailAuthRepository: EmailAuthRepository
) {

    fun signUpWithEmail(email: String, password: String) =
        emailAuthRepository.signUpWithEmail(email, password)
}