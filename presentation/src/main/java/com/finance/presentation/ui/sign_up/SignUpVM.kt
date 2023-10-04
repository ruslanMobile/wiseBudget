package com.finance.presentation.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val signUpState = MutableStateFlow(false)
    val _signUpState = signUpState.asStateFlow()
    fun signUpWithEmail(email: String, password: String) =
        emailAuthUseCase.signUpWithEmail(email, password).stateIn(
            scope = viewModelScope, // Use the appropriate coroutine scope
            started = SharingStarted.WhileSubscribed(5000), // Set the desired sharing behavior
            initialValue = false/* Provide an initial value or null if not needed */
        )

}