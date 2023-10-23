package com.finance.presentation.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.presentation.MainActivity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainVM = hiltViewModel()
) {


//    val suggestedDestinations by viewModel.suggestedDestinations.observeAsState()
//
//    val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) }
//    val craneScreenValues = CraneScreen.values()
//    val pagerState =
//        rememberPagerState(initialPage = CraneScreen.Fly.ordinal) { craneScreenValues.size }
    val context = LocalContext.current
    BackHandler(enabled = true) {
        finishAffinity(context as MainActivity)
    }

    val coroutineScope = rememberCoroutineScope()
    BackdropScaffold(
        modifier = Modifier,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        frontLayerShape = RoundedCornerShape(corner = CornerSize(20.dp)),
        frontLayerScrimColor = Color.Unspecified,
        appBar = {

        },
        backLayerContent = {

        },
        frontLayerContent = {

        }
    )
}