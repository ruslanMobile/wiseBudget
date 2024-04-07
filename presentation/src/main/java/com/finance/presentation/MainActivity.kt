package com.finance.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.finance.presentation.model.Screen.*
import com.finance.presentation.ui.login.LoginScreen
import com.finance.presentation.ui.login_or_signup.LogInOrSignUpScreen
import com.finance.presentation.ui.main_screen.MainScreen
import com.finance.presentation.ui.sign_up.SignUpScreen
import com.finance.presentation.ui.theme.WiseBudgetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        setContent {
            WiseBudgetTheme(darkTheme = false) {
                val navController = rememberNavController()
                val viewModel: MainActivityVM =
                    hiltViewModel(viewModelStoreOwner = this@MainActivity)

                NavHost(
                    navController = navController,
                    startDestination = if (viewModel.alreadyLoggedIn()) Main.route else "auth"
                ) {
                    navigation(route = "auth", startDestination = LoginOrSignUp.route) {
                        composable(LoginOrSignUp.route) {
                            LogInOrSignUpScreen(
                                navController,
                                hiltViewModel(viewModelStoreOwner = this@MainActivity)
                            )
                        }
                        composable(Login.route) {
                            LoginScreen(
                                navController,
                                hiltViewModel(viewModelStoreOwner = this@MainActivity)
                            )
                        }
                        composable(SignUp.route) {
                            SignUpScreen(
                                navController,
                                hiltViewModel(viewModelStoreOwner = this@MainActivity)
                            )
                        }
                    }
                    composable(Main.route) {
                        MainScreen()
                    }
                }
            }
        }
    }
}