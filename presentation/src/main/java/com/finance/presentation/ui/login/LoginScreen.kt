package com.finance.presentation.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.domain.repository.LoginState
import com.finance.presentation.R
import com.finance.presentation.ui.custom_ui.BasicAuthTextField
import com.finance.presentation.ui.theme.GreenDark
import com.finance.presentation.ui.theme.GreenDark2
import com.finance.presentation.ui.theme.Silver
import com.finance.presentation.utils.fontDimensionResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginVM = hiltViewModel()
) {

    val usernameState = remember {
        mutableStateOf("")
    }

    val passwordState = remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GreenDark2,
                            GreenDark
                        )
                    )
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(id = R.string.title_welcome_back),
                    style = TextStyle(
                        fontSize = fontDimensionResource(id = R.dimen.text_40),
                        color = Color.White,
                        fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                        textAlign = TextAlign.Center
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(id = R.string.label_login_to_your_account),
                    style = TextStyle(
                        fontSize = fontDimensionResource(id = R.dimen.text_16),
                        color = Color.White,
                        fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                        textAlign = TextAlign.Center
                    ),
                )
                BasicAuthTextField(
                    state = usernameState,
                    startIcon = R.drawable.ic_user,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.offset_56)),
                    hintText = R.string.hint_username,
                    borderBrush = Brush.horizontalGradient(listOf(GreenDark2, Silver))
                )

                BasicAuthTextField(
                    state = passwordState,
                    startIcon = R.drawable.ic_password,
                    modifier = Modifier.padding(0.dp, dimensionResource(id = R.dimen.offset_26)),
                    hintText = R.string.hint_password,
                    borderBrush = Brush.horizontalGradient(listOf(Silver, GreenDark2)),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.offset_56)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = {
                        viewModel.loginWithEmail(usernameState.value, passwordState.value)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Silver,
                        contentColor = GreenDark
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_login).uppercase(),
                        style = TextStyle(
                            fontSize = fontDimensionResource(id = R.dimen.text_16),
                            fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                        ),
                        modifier = Modifier.padding(0.dp, dimensionResource(id = R.dimen.offset_2))
                    )
                }

                Row(
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.offset_8))
                ) {
                    Text(
                        text = stringResource(id = R.string.label_dont_have_an_account),
                        style = TextStyle(
                            fontSize = fontDimensionResource(id = R.dimen.text_12),
                            fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                        ),
                    )
                    ClickableText(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.offset_4)),
                        text = AnnotatedString(stringResource(id = R.string.btn_Sign_up)),
                        style = TextStyle(
                            fontSize = fontDimensionResource(id = R.dimen.text_12),
                            fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                            textDecoration = TextDecoration.Underline,
                            color = Color.White
                        ),
                        onClick = {
                            navController.navigate("sign_up")
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = true, block = {
        viewModel._loginUpState.collect { loginState ->
            when (loginState) {
                LoginState.SuccessLogin -> navController.navigate("main")
                else -> {}
            }
        }
    })
}