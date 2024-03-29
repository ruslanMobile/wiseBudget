package com.finance.presentation.ui.categories.expenses_incomes

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import kotlin.collections.sumOf
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.presentation.R
import com.finance.presentation.model.Screen
import com.finance.presentation.ui.custom_ui.PagerLabel
import com.finance.presentation.ui.categories.CategoriesVM
import com.finance.presentation.ui.categories.TransitionUIState
import com.finance.presentation.ui.theme.GreenDark
import com.finance.presentation.ui.theme.GreenDark2
import com.finance.presentation.utils.fontDimensionResource
import kotlinx.coroutines.launch

enum class MainScreenFrontPager {
    EXPENSES, INCOMES
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ExpensesIncomesScreen(
    navController: NavHostController,
    viewModel: CategoriesVM = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val mainScreenValues = MainScreenFrontPager.values()
    val pagerState =
        rememberPagerState(initialPage = MainScreenFrontPager.EXPENSES.ordinal) { mainScreenValues.size }
    val animVal = remember { Animatable(0f) }
    val incomesReceiveState = viewModel._incomesReceiveState.collectAsState()
    val expensesReceiveState = viewModel._expensesReceiveState.collectAsState()

    LaunchedEffect(key1 = pagerState.currentPage) {
        viewModel.setIncomeOrExpenseState(pagerState.currentPage)
        animVal.snapTo(0f)
        animVal.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 400)
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.offset_16))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            PagerLabel(
                text = stringResource(id = R.string.btn_expenses),
                isSelected = pagerState.currentPage == MainScreenFrontPager.EXPENSES.ordinal,
                animVal = animVal.value,
            ) {
                coroutineScope.launch {
                    animVal.snapTo(0f)
                    pagerState.animateScrollToPage(MainScreenFrontPager.EXPENSES.ordinal)
                }
            }

            PagerLabel(
                text = stringResource(id = R.string.btn_incomes),
                isSelected = pagerState.currentPage == MainScreenFrontPager.INCOMES.ordinal,
                animVal = animVal.value
            ) {
                coroutineScope.launch {
                    animVal.snapTo(0f)
                    pagerState.animateScrollToPage(MainScreenFrontPager.INCOMES.ordinal)
                }
            }
        }
        HorizontalPager(state = pagerState) { page ->
            when (mainScreenValues[page]) {
                MainScreenFrontPager.EXPENSES -> {
                    when (expensesReceiveState.value) {
                        is TransitionUIState.Success -> {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        top = dimensionResource(
                                            id = R.dimen.offset_32
                                        )
                                    )
                            ) {
                                items(
                                    items = (expensesReceiveState.value as? TransitionUIState.Success)?.list
                                        ?: mutableListOf(),
                                    key = { it.name }) { category ->
                                    val bottomBorder =
                                        dimensionResource(id = R.dimen.offset_4).value
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(dimensionResource(id = R.dimen.offset_62))
                                            .weight(5f)
                                            .padding(horizontal = dimensionResource(id = R.dimen.offset_26))
                                            .drawBehind {
                                                drawLine(
                                                    color = GreenDark2,
                                                    start = Offset(
                                                        size.width / 5,
                                                        size.height
                                                    ),
                                                    end = Offset(size.width, size.height),
                                                    strokeWidth = bottomBorder,
                                                    cap = StrokeCap.Round
                                                )
                                            }
                                            .clickable {
                                                navController.navigate("${Screen.ExpensesLog.route}/${category.name}")
                                            },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = category.icon),
                                                contentDescription = "",
                                                tint = Color.Unspecified,
                                                modifier = Modifier
                                                    .size(dimensionResource(id = R.dimen.offset_32))
                                            )
                                        }
                                        Text(
                                            text = category.name,
                                            modifier = Modifier.weight(2f),
                                            fontWeight = FontWeight(500),
                                            style = TextStyle(
                                                fontSize = fontDimensionResource(id = R.dimen.text_16),
                                                color = GreenDark,
                                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                                                textAlign = TextAlign.Start
                                            ),
                                        )

                                        Column(
                                            modifier = Modifier.weight(2f),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "$${category.list?.sumOf { it.amount ?: 0 }}",
                                                style = TextStyle(
                                                    fontSize = fontDimensionResource(id = R.dimen.text_16),
                                                    color = GreenDark,
                                                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                                                )
                                            )
                                            Text(
                                                text = (category.list?.size ?: 0).toString(),
                                                style = TextStyle(
                                                    fontSize = fontDimensionResource(id = R.dimen.text_12),
                                                    color = GreenDark2,
                                                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_medium)),
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        is TransitionUIState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(top = dimensionResource(id = R.dimen.offset_32))
                                        .align(Alignment.Center),
                                    color = GreenDark
                                )
                            }
                        }

                        else -> {}
                    }
                }

                MainScreenFrontPager.INCOMES -> {
                    when (incomesReceiveState.value) {
                        is TransitionUIState.Success -> {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        top = dimensionResource(
                                            id = R.dimen.offset_32
                                        )
                                    )
                            ) {
                                items(
                                    items = (incomesReceiveState.value as? TransitionUIState.Success)?.list
                                        ?: mutableListOf(),
                                    key = { it.name }) { category ->
                                    val bottomBorder =
                                        dimensionResource(id = R.dimen.offset_4).value
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(dimensionResource(id = R.dimen.offset_62))
                                            .weight(5f)
                                            .padding(horizontal = dimensionResource(id = R.dimen.offset_26))
                                            .drawBehind {
                                                drawLine(
                                                    color = GreenDark2,
                                                    start = Offset(
                                                        size.width / 5,
                                                        size.height
                                                    ),
                                                    end = Offset(size.width, size.height),
                                                    strokeWidth = bottomBorder,
                                                    cap = StrokeCap.Round
                                                )
                                            }
                                            .clickable {
                                                navController.navigate("${Screen.IncomeLog.route}/${category.name}")
                                            },
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = category.icon),
                                                contentDescription = "",
                                                tint = Color.Unspecified,
                                                modifier = Modifier
                                                    .size(dimensionResource(id = R.dimen.offset_32))
                                            )
                                        }
                                        Text(
                                            text = category.name,
                                            modifier = Modifier.weight(2f),
                                            fontWeight = FontWeight(500),
                                            style = TextStyle(
                                                fontSize = fontDimensionResource(id = R.dimen.text_16),
                                                color = GreenDark,
                                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                                                textAlign = TextAlign.Start
                                            ),
                                        )

                                        Column(
                                            modifier = Modifier.weight(2f),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "$${category.list?.sumOf { it.amount ?: 0 } ?: 0}",
                                                style = TextStyle(
                                                    fontSize = fontDimensionResource(id = R.dimen.text_16),
                                                    color = GreenDark,
                                                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_semi_bold)),
                                                )
                                            )
                                            Text(
                                                text = (category.list?.size ?: 0).toString(),
                                                style = TextStyle(
                                                    fontSize = fontDimensionResource(id = R.dimen.text_12),
                                                    color = GreenDark2,
                                                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_medium)),
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        is TransitionUIState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(top = dimensionResource(id = R.dimen.offset_32))
                                        .align(Alignment.Center),
                                    color = GreenDark
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}