package com.finance.presentation.ui.categories

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.finance.domain.model.TransactionBase
import com.finance.presentation.R
import com.finance.presentation.model.Screen
import com.finance.presentation.ui.categories.expense_log.ExpenseLogScreen
import com.finance.presentation.ui.categories.expenses_incomes.ExpensesIncomesScreen
import com.finance.presentation.ui.categories.expenses_incomes.MainScreenFrontPager
import com.finance.presentation.ui.categories.income_log.IncomeLogScreen
import com.finance.presentation.ui.custom_ui.CustomProgressBar
import com.finance.presentation.ui.theme.shadow_5
import com.finance.presentation.utils.areDatesInSameMonth
import com.finance.presentation.utils.calculateExpensePercentage
import com.finance.presentation.utils.fontDimensionResource
import com.finance.presentation.utils.getMonthNameFromLongDate

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
fun CategoriesScreen(
    navController: NavHostController,
    viewModel: CategoriesVM = hiltViewModel()
) {

    val selectedDateRange = viewModel.selectedDateRange.collectAsState()
    val expensesOrIncomesSelected = viewModel.expensesOrIncomesSelected.collectAsState()

    val incomesReceiveState = viewModel._incomesReceiveState.collectAsState(
        TransitionUIState.Initial
    )
    val expensesReceiveState = viewModel._expensesReceiveState.collectAsState(
        TransitionUIState.Initial
    )

    val expensesAmountOfMoney = remember {
        derivedStateOf {
            countAmountOfMoney(
                selectedDateRange,
                expensesReceiveState
            )
        }
    }

    val incomesAmountOfMoney = remember {
        derivedStateOf {
            countAmountOfMoney(
                selectedDateRange,
                incomesReceiveState
            )
        }
    }

    val overallAmountOfMoney = remember {
        derivedStateOf {
            countAmountOfMoney(
                selectedDateRange,
                if (expensesOrIncomesSelected.value == MainScreenFrontPager.EXPENSES.ordinal) expensesReceiveState else incomesReceiveState
            )
        }
    }

//    val suggestedDestinations by viewModel.suggestedDestinations.observeAsState()
//    val onPeopleChanged: (Int) -> Unit = { viewModel.updatePeople(it) }

    BackdropScaffold(
        modifier = Modifier,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed),
        frontLayerShape = RoundedCornerShape(
            topStart = dimensionResource(id = R.dimen.offset_20),
            topEnd = dimensionResource(id = R.dimen.offset_20)
        ),
        frontLayerScrimColor = Color.Unspecified,
        frontLayerBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
        backLayerBackgroundColor = MaterialTheme.colorScheme.surfaceTint,
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
                    IconButton(onClick = {
                        viewModel.changeSelectedMonth(-1)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_arrow_left),
                            tint = MaterialTheme.colorScheme.onPrimary,
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
                            text = getMonthNameFromLongDate(selectedDateRange.value),
                            style = TextStyle(
                                fontSize = fontDimensionResource(id = R.dimen.text_26),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                                textAlign = TextAlign.Center
                            )
                        )

                        Text(
                            text = "$${overallAmountOfMoney.value}",
                            style = TextStyle(
                                fontSize = fontDimensionResource(id = R.dimen.text_48),
                                color = MaterialTheme.colorScheme.inversePrimary,
                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_bold)),
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                    IconButton(onClick = {
                        viewModel.changeSelectedMonth(1)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_arrow_left),
                            tint = MaterialTheme.colorScheme.onPrimary,
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
                color = shadow_5
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.offset_20),
                            start = dimensionResource(id = R.dimen.offset_20),
                            end = dimensionResource(id = R.dimen.offset_20)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.label_month_budget),
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold))
                            )
                        )

                        Text(
                            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.offset_20)),
                            text = "$${incomesAmountOfMoney.value}",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.secondary,
                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_medium_italic))
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "${
                                calculateExpensePercentage(
                                    incomesAmountOfMoney.value,
                                    expensesAmountOfMoney.value
                                ).toInt()
                            }%",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold))
                            )
                        )
                    }
                    CustomProgressBar(
                        outModifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.offset_6),
                            start = dimensionResource(id = R.dimen.offset_20),
                            end = dimensionResource(id = R.dimen.offset_20)
                        ),
                        Modifier
                            .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.offset_20))),
                        dimensionResource(id = R.dimen.offset_10),
                        Color.Gray,
                        Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.surfaceTint,
                                MaterialTheme.colorScheme.onSurface
                            )
                        ),
                        calculateExpensePercentage(
                            incomesAmountOfMoney.value,
                            expensesAmountOfMoney.value
                        ).toInt()
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.offset_20))
                            .fillMaxSize()
                            .shadow(
                                dimensionResource(id = R.dimen.offset_62),
                                shape = RoundedCornerShape(
                                    topEnd = dimensionResource(id = R.dimen.offset_32),
                                    topStart = dimensionResource(id = R.dimen.offset_32)
                                )
                            )
                            .clip(
                                RoundedCornerShape(
                                    topEnd = dimensionResource(id = R.dimen.offset_32),
                                    topStart = dimensionResource(id = R.dimen.offset_32)
                                )
                            )
                            .background(MaterialTheme.colorScheme.primaryContainer)

                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.ExpensesIncomes.route
                        ) {

                            composable(
                                route = Screen.ExpensesIncomes.route,
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
                                "${Screen.ExpensesLog.route}/{category}",
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
                                "${Screen.IncomeLog.route}/{category}",
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
        }
    )
}

fun countAmountOfMoney(
    selectedDateRange: State<Long>,
    data: State<TransitionUIState>,
): Int {
    if (data.value is TransitionUIState.Success) {
        val list = mutableListOf<TransactionBase>()
        (data.value as? TransitionUIState.Success)?.list?.forEach {
            it.list?.forEach { transaction ->
                transaction.dateLong?.let { transactionDate ->
                    if (areDatesInSameMonth(
                            transactionDate,
                            selectedDateRange.value
                        )
                    ) {
                        list.add(transaction)
                    }
                }
            }
        }
        return list.sumOf { it.amount ?: 0 }
    } else return 0
}