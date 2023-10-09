package com.finance.presentation.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.repository.SignedState
import com.finance.domain.usecase.EmailAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val emailAuthUseCase: EmailAuthUseCase
) : ViewModel() {

    private var signUpState = MutableStateFlow<SignedState>(SignedState.DefaultSignUp)
    val _signUpState = signUpState.asStateFlow()
    fun signUpWithEmail(email: String, password: String) = viewModelScope.launch {
        emailAuthUseCase.signUpWithEmail(email, password) {
            signUpState.value = it
        }
    }
}