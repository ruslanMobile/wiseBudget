package com.finance.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.finance.presentation.ui.login.LoginScreen
import com.finance.presentation.ui.login_or_signup.LogInOrSignUpScreen
import com.finance.presentation.ui.sign_up.SignUpScreen
import com.finance.presentation.ui.theme.WiseBudgetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WiseBudgetTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "auth") {
                    navigation(route = "auth", startDestination = "loginOrSignup") {
                        composable("loginOrSignup") {
                            LogInOrSignUpScreen(navController, hiltViewModel(viewModelStoreOwner = this@MainActivity))
                        }
                        composable("login") {
                            LoginScreen(navController, hiltViewModel(viewModelStoreOwner = this@MainActivity))
                        }
                        composable("sign_up") {
                            SignUpScreen(navController, hiltViewModel(viewModelStoreOwner = this@MainActivity))
                        }
                    }
                }
            }
        }
    }
}