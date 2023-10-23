package com.finance.presentation

import androidx.lifecycle.ViewModel
import com.finance.domain.usecase.GoogleAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val googleAuthUseCase: GoogleAuthUseCase
) : ViewModel() {

    fun alreadyLoggedIn() = googleAuthUseCase.alreadyLoggedIn()

}