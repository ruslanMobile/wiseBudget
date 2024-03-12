package com.finance.presentation.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finance.domain.repository.SignedState
import com.finance.domain.usecase.EmailAuthUseCase
import com.finance.presentation.ui.login.LoginVM
import com.finance.presentation.utils.isEmailValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val emailAuthUseCase: EmailAuthUseCase
) : ViewModel() {

    private var signUpState = MutableStateFlow<SignedState>(SignedState.DefaultSignUp)
    val _signUpState = signUpState.asStateFlow()
    fun signUpWithEmail(email: String, password: String, repeatPassword: String) = viewModelScope.launch {
        if (credentialsValidation(email, password, repeatPassword)) {
            signUpState.tryEmit(SignedState.LoadingSignUp())
            emailAuthUseCase.signUpWithEmail(email, password) {
                signUpState.value = it
            }
        }
    }

    private fun credentialsValidation(email: String, password: String, repeatPassword: String): Boolean {
        if (!isEmailValid(email)) {
            signUpState.tryEmit(SignedState.EmailIncorrect())
            return false
        } else if (password.length < LoginVM.MINIMUM_PASSWORD_LENGTH) {
            signUpState.tryEmit(SignedState.PasswordIncorrect())
            return false
        }else if(repeatPassword != password){
            signUpState.tryEmit(SignedState.RepeatPasswordIncorrect())
            return false
        }
        return true
    }

}