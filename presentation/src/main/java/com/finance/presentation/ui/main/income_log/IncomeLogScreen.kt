package com.finance.presentation.ui.main.income_log

import android.util.Log
import android.view.Gravity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.domain.model.Income
import com.finance.domain.repository.IncomeLogState
import com.finance.presentation.R
import com.finance.presentation.ui.custom_ui.BasicLowOutlineTextField
import com.finance.presentation.ui.main.MainVM
import com.finance.presentation.ui.theme.GreenDark
import com.finance.presentation.ui.theme.Silver
import com.finance.presentation.utils.fontDimensionResource
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun IncomeLogScreen(
    navController: NavHostController,
    viewModel: MainVM = hiltViewModel(),
    category: String?
) {
    val incomeName = remember {
        mutableStateOf("")
    }

    val incomeAmount = remember {
        mutableStateOf("")
    }

    val showDatePicker = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.offset_26)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.offset_16))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.label_add_income),
                fontSize = fontDimensionResource(
                    id = R.dimen.text_18
                ),
                fontWeight = FontWeight(700)
            )
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "",
                    modifier = Modifier.size(dimensionResource(id = R.dimen.offset_26))
                )
            }
        }

        Text(
            text = stringResource(id = R.string.label_income_name),
            style = TextStyle(
                fontSize = fontDimensionResource(id = R.dimen.text_12),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.offset_16))
        )

        BasicLowOutlineTextField(
            incomeName
        )

        Text(
            text = stringResource(id = R.string.label_amount),
            style = TextStyle(
                fontSize = fontDimensionResource(id = R.dimen.text_12),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.offset_16))
        )

        BasicLowOutlineTextField(
            incomeAmount,
            KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.offset_16)),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.label_category_dots),
                style = TextStyle(
                    fontSize = fontDimensionResource(id = R.dimen.text_12),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                    textAlign = TextAlign.Start
                ),
            )

            Text(
                text = category ?: "",
                style = TextStyle(
                    fontSize = fontDimensionResource(id = R.dimen.text_10),
                    color = Silver,
                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.offset_6))
                    .background(
                        color = GreenDark, RoundedCornerShape(
                            dimensionResource(id = R.dimen.offset_6)
                        )
                    )
                    .padding(horizontal = dimensionResource(id = R.dimen.offset_6))
            )
        }

        Text(
            text = stringResource(id = R.string.label_date),
            style = TextStyle(
                fontSize = fontDimensionResource(id = R.dimen.text_12),
                color = Color.Black,
                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.offset_16))
        )

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = Instant.now().toEpochMilli()
        )

        val selectedDate = datePickerState.selectedDateMillis?.let {
            Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC)
        }

        Box(
            modifier = Modifier
                .height(46.dp)
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.offset_6))
                .clickable {
                    showDatePicker.value = !showDatePicker.value
                },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                rememberDrawablePainter(
                    drawable = ContextCompat.getDrawable(
                        LocalContext.current,
                        R.drawable.bg_expense_log_date
                    )
                ),
                contentDescription = ""
            )
            Text(
                text = stringResource(
                    id = R.string.label_selected_date,
                    selectedDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        ?: stringResource(id = R.string.label_no_selection)
                ),
                style = TextStyle(
                    fontSize = fontDimensionResource(id = R.dimen.text_14),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                    textAlign = TextAlign.Start,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Proportional,
                        trim = LineHeightStyle.Trim.None
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = CenterVertically)
                    .padding(start = dimensionResource(id = R.dimen.offset_12))
            )
        }

        OutlinedButton(
            onClick = {
                viewModel.addIncomeToDb(
                    Income(
                        incomeName = incomeName.value,
                        category = category ?: "",
                        amount = incomeAmount.value.toInt(),
                        dateLong = datePickerState.selectedDateMillis ?: 0L
                    )
                )
            },
            border = BorderStroke(dimensionResource(id = R.dimen.offset_2), GreenDark),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(top = dimensionResource(id = R.dimen.offset_32)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.btn_done).uppercase(),
                style = TextStyle(
                    fontSize = fontDimensionResource(id = R.dimen.text_16),
                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                ),
                modifier = Modifier.padding(0.dp, dimensionResource(id = R.dimen.offset_2))
            )
        }

        if (showDatePicker.value) {
            DatePickerDialog(
                shape = RoundedCornerShape(6.dp),
                onDismissRequest = { showDatePicker.value = false },
                confirmButton = {},
            ) {
                val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
                dialogWindowProvider.window.setGravity(Gravity.CENTER_VERTICAL)
                DatePicker(
                    state = datePickerState,
                    dateValidator = { timestamp ->
                        timestamp > Instant.now().toEpochMilli()
                    }
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel._incomeLogState.collect{ state ->
            Log.e("MyLog","IT: $state")
            when(state){
                IncomeLogState.SuccessIncomeLogState -> {
                    Log.e("MyLog","SuccessIncomeLogState")
                    navController.popBackStack()
                }
                else -> {}
            }
        }
    })
}