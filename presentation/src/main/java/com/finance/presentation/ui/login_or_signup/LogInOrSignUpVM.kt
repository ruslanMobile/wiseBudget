package com.finance.presentation.ui.login_or_signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.repository.GoogleAuthRepository
import com.finance.domain.repository.LoginState
import com.finance.presentation.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInOrSignUpVM @Inject constructor(
    private val application: Application,
    private val googleAuthRepository: GoogleAuthRepository
) : ViewModel() {

    private var loginUpState = MutableStateFlow<LoginState>(LoginState.DefaultLogin)
    val _loginUpState = loginUpState.asStateFlow()

    fun getGoogleSignInClient(): GoogleSignInClient {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(application, signInOptions)
    }

    fun authenticationWithGoogle(idToken: String?) = viewModelScope.launch{
        googleAuthRepository.authenticationWithGoogle(idToken){
            loginUpState.value = it
        }
    }
}