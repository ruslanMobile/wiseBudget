package com.finance.presentation.ui.profile

import android.app.Application
import androidx.lifecycle.ViewModel
import com.finance.domain.repository.LogoutState
import com.finance.presentation.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val application: Application
) : ViewModel() {

    private var logOutState = MutableSharedFlow<LogoutState>(replay = 1)
    val _logOutState = logOutState.asSharedFlow()

    private fun getGoogleSignInClient(): GoogleSignInClient {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(application, signInOptions)
    }

    fun logOut() {
        logOutState.tryEmit(LogoutState.LoadingLogout())
        FirebaseAuth.getInstance().signOut()
        getGoogleSignInClient().signOut()
            .addOnSuccessListener { task ->
                logOutState.tryEmit(LogoutState.SuccessLogout)
            }
            .addOnFailureListener {
                logOutState.tryEmit(LogoutState.FailLogout(it.message))
            }
    }
}