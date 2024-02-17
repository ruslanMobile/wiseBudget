package com.finance.presentation.ui.main.expenses_incomes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.finance.presentation.ui.main.MainVM
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
    viewModel: MainVM = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val expenses = viewModel.expenses.collectAsState()
    val incomes = viewModel.incomes.collectAsState()
    val mainScreenValues = MainScreenFrontPager.values()
    val pagerState =
        rememberPagerState(initialPage = MainScreenFrontPager.EXPENSES.ordinal) { mainScreenValues.size }

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
            Text(
                text = stringResource(id = R.string.btn_expenses),
                fontSize = fontDimensionResource(
                    id = R.dimen.text_20
                ),
                fontWeight = FontWeight(700),
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(MainScreenFrontPager.EXPENSES.ordinal)
                    }
                }
            )
            Text(
                text = stringResource(id = R.string.btn_incomes),
                fontSize = fontDimensionResource(
                    id = R.dimen.text_20
                ),
                fontWeight = FontWeight(700),
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(MainScreenFrontPager.INCOMES.ordinal)
                    }
                }
            )
        }
        HorizontalPager(state = pagerState) { page ->
            when (mainScreenValues[page]) {
                MainScreenFrontPager.EXPENSES -> {
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
                            items = expenses.value,
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
                                        navController.navigate("expense_log/${category.name}")
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

                MainScreenFrontPager.INCOMES -> {
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
                            items = incomes.value,
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
                                        navController.navigate("income_log/${category.name}")
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
            }
        }
    }
}