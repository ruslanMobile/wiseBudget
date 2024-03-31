package com.finance.presentation.ui.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.finance.domain.repository.LogoutState
import com.finance.presentation.MainActivity
import com.finance.presentation.R
import com.finance.presentation.ui.theme.GreenDark
import com.finance.presentation.ui.theme.GreenDark2
import com.finance.presentation.ui.theme.GreenLight
import com.finance.presentation.ui.theme.Silver
import com.finance.presentation.utils.fontDimensionResource

@Composable
fun ProfileScreen(
    viewModel: ProfileVM = hiltViewModel()
) {
    val logoutState =
        viewModel._logOutState.collectAsStateWithLifecycle(initialValue = LogoutState.DefaultLogout)
    when (logoutState.value) {
        is LogoutState.SuccessLogout -> {
            Log.e("MyLog", "SuccessLogout")
            val context = LocalContext.current
            val activity = (context as? MainActivity)
            val intent = activity?.intent
            activity?.finish()
            context.startActivity(intent)
        }

        is LogoutState.FailLogout -> {
            Toast.makeText(
                LocalContext.current, stringResource(id = R.string.label_logout_error),
                Toast.LENGTH_SHORT
            ).show()
        }

        else -> {}
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Silver
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Silver)
                .padding(top = dimensionResource(id = R.dimen.offset_32)),
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.offset_16))
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.offset_16)))
                    .background(GreenDark2)
                    .clickable {
                        viewModel.logOut()
                    }
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(id = R.dimen.offset_12)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.offset_16))
                            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.offset_10)))
                            .background(GreenLight)
                            .padding(dimensionResource(id = R.dimen.offset_6))
                    ) {
                        Icon(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.offset_32)),
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "",
                            tint = GreenDark2
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.offset_16)),
                        text = stringResource(id = R.string.btn_logout), style = TextStyle(
                            fontSize = fontDimensionResource(id = R.dimen.text_18),
                            color = Silver,
                            fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        if (logoutState.value is LogoutState.LoadingLogout) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.offset_56))
                        .align(Alignment.Center),
                    color = GreenDark
                )
            }
        }
    }
}