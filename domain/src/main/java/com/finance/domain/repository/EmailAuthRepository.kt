package com.finance.domain.repository

import kotlinx.coroutines.flow.Flow

interface EmailAuthRepository {

    fun signUpWithEmail(email: String, password: String): Flow<Boolean>
}