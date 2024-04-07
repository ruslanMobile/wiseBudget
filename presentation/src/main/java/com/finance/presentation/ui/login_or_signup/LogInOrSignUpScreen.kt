package com.finance.presentation.ui.login_or_signup

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.domain.repository.LoginState
import com.finance.presentation.R
import com.finance.presentation.utils.fontDimensionResource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun LogInOrSignUpScreen(
    navController: NavHostController,
    viewModel: LogInOrSignUpVM = hiltViewModel()
) {

    val context = LocalContext.current
    val authResultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                        .getResult(ApiException::class.java)
                    if (task != null) {
                        viewModel.authenticationWithGoogle(task.idToken)
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.label_google_auth_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
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
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.inversePrimary
                        )
                    )
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                )

                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(id = R.string.title_app_name),
                    style = TextStyle(
                        fontSize = fontDimensionResource(id = R.dimen.text_40),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                        textAlign = TextAlign.Center
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = stringResource(id = R.string.text_first_screen_message),
                    style = TextStyle(
                        fontSize = fontDimensionResource(id = R.dimen.text_16),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                        textAlign = TextAlign.Center
                    ),
                )

                Button(
                    onClick = {
                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .padding(0.dp, dimensionResource(id = R.dimen.offset_32))
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

                OutlinedButton(
                    onClick = {
                        navController.navigate("sign_up")
                    },
                    border = BorderStroke(dimensionResource(id = R.dimen.offset_2), MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth(0.85f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_sign_up).uppercase(),
                        style = TextStyle(
                            fontSize = fontDimensionResource(id = R.dimen.text_16),
                            fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                        ),
                        modifier = Modifier.padding(0.dp, dimensionResource(id = R.dimen.offset_2))
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(0.dp, dimensionResource(id = R.dimen.offset_62), 0.dp, 0.dp)
                        .fillMaxWidth(0.85f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.offset_2))
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {}
                    Text(
                        modifier = Modifier.weight(2f),
                        text = stringResource(id = R.string.label_or_continue_with),
                        style = TextStyle(
                            fontSize = fontDimensionResource(id = R.dimen.text_14),
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                            textAlign = TextAlign.Center
                        )
                    )
                    Box(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.offset_2))
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {}
                }

                IconButton(
                    onClick = {
                        authResultLauncher.launch(viewModel.getGoogleSignInClient().signInIntent)
                    },
                    modifier = Modifier
                        .padding(0.dp, dimensionResource(id = R.dimen.offset_26), 0.dp, 0.dp)
                        .size(dimensionResource(id = R.dimen.offset_56))
                        .shadow(dimensionResource(id = R.dimen.offset_4), CircleShape)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(dimensionResource(id = R.dimen.offset_12))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "",
                        tint = Color.Unspecified
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