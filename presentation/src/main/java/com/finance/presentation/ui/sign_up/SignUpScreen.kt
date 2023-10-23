package com.finance.presentation.ui.sign_up

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.domain.repository.SignedState
import com.finance.presentation.R
import com.finance.presentation.ui.custom_ui.BasicAuthTextField
import com.finance.presentation.ui.login.LoginVM
import com.finance.presentation.ui.theme.GreenDark
import com.finance.presentation.ui.theme.GreenDark2
import com.finance.presentation.ui.theme.Silver
import com.finance.presentation.utils.fontDimensionResource
import com.finance.presentation.utils.requestNotCheckError
import kotlinx.coroutines.flow.collect

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpVM = hiltViewModel()
) {

    val usernameState = remember {
        mutableStateOf("")
    }

    val passwordState = remember {
        mutableStateOf("")
    }

    val repeatPasswordState = remember {
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
                    text = stringResource(id = R.string.title_register),
                    style = TextStyle(
                        fontSize = fontDimensionResource(id = R.dimen.text_40),
                        color = Color.White,
                        fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                        textAlign = TextAlign.Center
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(id = R.string.label_create_your_account),
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
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.offset_26)),
                    hintText = R.string.hint_password,
                    borderBrush = Brush.horizontalGradient(listOf(Silver, GreenDark2)),
                    visualTransformation = PasswordVisualTransformation()
                )

                BasicAuthTextField(
                    state = repeatPasswordState,
                    startIcon = R.drawable.ic_password,
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.offset_26)),
                    hintText = R.string.hint_repeat_password,
                    borderBrush = Brush.horizontalGradient(listOf(GreenDark2, Silver)),
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
                        viewModel.signUpWithEmail(usernameState.value, passwordState.value)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Silver,
                        contentColor = GreenDark
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_register).uppercase(),
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
                        text = stringResource(id = R.string.label_already_have_an_account),
                        style = TextStyle(
                            fontSize = fontDimensionResource(id = R.dimen.text_12),
                            fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                        ),
                    )
                    ClickableText(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.offset_4)),
                        text = AnnotatedString(stringResource(id = R.string.btn_Login)),
                        style = TextStyle(
                            fontSize = fontDimensionResource(id = R.dimen.text_12),
                            fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                            textDecoration = TextDecoration.Underline,
                            color = Color.White
                        ),
                        onClick = {
                            navController.navigate("login")
                        }
                    )
                }
            }
        }
    }


    LaunchedEffect(key1 = true, block = {
        viewModel._signUpState.collect{ signUpState ->
            Log.e("MyLog","IT: $signUpState")
            when(signUpState){
                SignedState.SuccessSignUp ->navController.navigate("main")
                else -> {}
            }
        }
    })
}