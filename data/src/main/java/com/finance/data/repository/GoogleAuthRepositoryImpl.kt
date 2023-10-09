package com.finance.data.repository

import com.finance.domain.repository.GoogleAuthRepository
import com.finance.domain.repository.LoginState
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class GoogleAuthRepositoryImpl @Inject constructor(
) : GoogleAuthRepository {

    override fun authenticationWithGoogle(idToken: String?, resFunc: (LoginState) -> Unit) {
        val auth = Firebase.auth
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnSuccessListener { task ->
                resFunc.invoke(LoginState.SuccessLogin)
            }
            .addOnFailureListener { e ->
                resFunc.invoke(LoginState.FailLogin(e.message))
            }
    }
}