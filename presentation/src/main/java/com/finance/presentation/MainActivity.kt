package com.finance.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.finance.presentation.ui.login_or_signup.LogInOrSignUpScreen
import com.finance.presentation.ui.theme.WiseBudgetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            WiseBudgetTheme {
                LogInOrSignUpScreen()
            }
        }
    }
}