package com.finance.data.repository

import com.finance.domain.repository.LoginState
import com.finance.domain.repository.EmailAuthRepository
import com.finance.domain.repository.SignedState
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import com.google.firebase.auth.ktx.auth

class EmailAuthRepositoryImpl @Inject constructor(

) : EmailAuthRepository {

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        func: (SignedState) -> Unit
    ) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { task ->
                func.invoke(SignedState.SuccessSignUp)
            }
            .addOnFailureListener { e ->
                func.invoke(SignedState.FailSignUp(e.message))
            }
    }

    override suspend fun loginWithEmail(
        email: String,
        password: String,
        func: (LoginState) -> Unit
    ) {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { task ->
                func.invoke(LoginState.SuccessLogin)
            }
            .addOnFailureListener { e ->
                func.invoke(LoginState.FailLogin(e.message))
            }
    }
}