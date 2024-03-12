package com.finance.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.repository.LoginState
import com.finance.domain.usecase.EmailAuthUseCase
import com.finance.presentation.R
import com.finance.presentation.utils.isEmailValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val emailAuthUseCase: EmailAuthUseCase
) : ViewModel() {

    private var loginUpState = MutableSharedFlow<LoginState>(replay = 1)
    val _loginUpState = loginUpState.asSharedFlow()

    fun loginWithEmail(email: String, password: String) = viewModelScope.launch {
        if (credentialsValidation(email, password)) {
            loginUpState.tryEmit(LoginState.LoadingLogin())
            emailAuthUseCase.loginWithEmail(email, password) {
                loginUpState.tryEmit(it)
            }
        }
    }

     fun credentialsValidation(email: String, password: String): Boolean {
        if (!isEmailValid(email)) {
            loginUpState.tryEmit(LoginState.EmailIncorrect())
            return false
        } else if (password.length < MINIMUM_PASSWORD_LENGTH) {
            loginUpState.tryEmit(LoginState.PasswordIncorrect(R.string.label_password_is_short))
            return false
        }
        return true
    }

    companion object {
        const val MINIMUM_PASSWORD_LENGTH = 8
    }
}