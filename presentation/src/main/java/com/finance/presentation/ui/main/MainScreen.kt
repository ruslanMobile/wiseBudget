package com.finance.presentation.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finance.presentation.MainActivity
import com.finance.presentation.MainActivityVM
import com.finance.presentation.R
import com.finance.presentation.ui.login.LoginScreen
import com.finance.presentation.ui.login_or_signup.LogInOrSignUpScreen
import com.finance.presentation.ui.main.expense_log.ExpenseLogScreen
import com.finance.presentation.ui.main.expenses_incomes.ExpensesIncomesScreen
import com.finance.presentation.ui.main.income_log.IncomeLogScreen
import com.finance.presentation.ui.sign_up.SignUpScreen
import com.finance.presentation.ui.theme.GreenDark
import com.finance.presentation.ui.theme.GreenDark2
import com.finance.presentation.ui.theme.Silver
import com.finance.presentation.utils.fontDimensionResource


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainVM = hiltViewModel()
) {


    //Log.e("MyLog","${expenses.value}")

//    val suggestedDestinations by viewModel.suggestedDestinations.observeAsState()
//
//    val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) }

    val context = LocalContext.current
    BackHandler(enabled = true) {
        finishAffinity(context as MainActivity)
    }

    val coroutineScope = rememberCoroutineScope()
    BackdropScaffold(
        modifier = Modifier,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed),
        frontLayerShape = RoundedCornerShape(
            topStart = dimensionResource(id = R.dimen.offset_20),
            topEnd = dimensionResource(id = R.dimen.offset_20)
        ),
        frontLayerScrimColor = Color.Unspecified,
        frontLayerBackgroundColor = Silver,
        backLayerBackgroundColor = GreenDark,
        peekHeight = 20.dp,
        headerHeight = dimensionResource(id = R.dimen.offset_120),
        stickyFrontLayer = true,
        appBar = {

        },
        backLayerContent = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_arrow_left),
                            tint = Silver,
                            contentDescription = "",
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.offset_40))
                                .weight(1f)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(12f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "October",
                            style = TextStyle(
                                fontSize = fontDimensionResource(id = R.dimen.text_26),
                                color = Color.White,
                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                                textAlign = TextAlign.Center
                            )
                        )

                        Text(
                            text = "$43,550",
                            style = TextStyle(
                                fontSize = fontDimensionResource(id = R.dimen.text_48),
                                color = Color.White,
                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_bold)),
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_arrow_left),
                            tint = Silver,
                            contentDescription = "",
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.offset_40))
                                .rotate(180f)
                                .weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
        frontLayerContent = {

            val navController = rememberNavController()
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "expenses_incomes_list"
                    ) {

                        composable(
                            route = "expenses_incomes_list",
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(700)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(700)
                                )
                            }
                        ) {
                            ExpensesIncomesScreen(
                                navController,
                                viewModel
                            )
                        }
                        composable(
                            "expense_log/{category}",
                            arguments = listOf(navArgument("category") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            ExpenseLogScreen(
                                navController,
                                viewModel,
                                backStackEntry.arguments?.getString("category")
                            )
                        }
                        composable(
                            "income_log/{category}",
                            arguments = listOf(navArgument("category") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            IncomeLogScreen(
                                navController,
                                viewModel,
                                backStackEntry.arguments?.getString("category")
                            )
                        }
                    }
                }
            }
        }
    )
}