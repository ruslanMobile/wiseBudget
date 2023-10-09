package com.finance.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.repository.LoginState
import com.finance.domain.usecase.EmailAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val emailAuthUseCase: EmailAuthUseCase
) : ViewModel() {

    private var loginUpState = MutableStateFlow<LoginState>(LoginState.DefaultLogin)
    val _loginUpState = loginUpState.asStateFlow()

    fun loginWithEmail(email: String, password: String) = viewModelScope.launch {
        emailAuthUseCase.loginWithEmail(email, password) {
            loginUpState.value = it
        }
    }
}