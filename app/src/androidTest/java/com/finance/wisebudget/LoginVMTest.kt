package com.finance.wisebudget

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.runner.AndroidJUnit4
import com.finance.presentation.ui.login.LoginVM
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginVMTest {

    lateinit var loginVM: LoginVM

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        hiltRule.inject()
        composeTestRule.setContent {
            loginVM = hiltViewModel()
        }
    }

    @Test
    fun loginWithWrongEmailTemplate_returnsError(){
        assert(loginVM.credentialsValidation("rusik@gmail.c", "12345678"))
    }

    @Test
    fun loginWithShortPassword_returnsError(){
        assert(loginVM.credentialsValidation("rusik@gmail.com", "1234567"))
    }

    @Test
    fun loginWithCorrectCredentials_returnsSuccess(){
        assert(loginVM.credentialsValidation("rusik@gmail.com", "12345678"))
    }
}