package com.finance.presentation.ui.categories.create_category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.finance.domain.repository.ExpenseLogState
import com.finance.presentation.R
import com.finance.presentation.model.Screen
import com.finance.presentation.ui.custom_ui.BasicLowOutlineTextField
import com.finance.presentation.utils.fontDimensionResource

@Composable
fun CreateCategoryScreen(
    outNavController: NavHostController,
    navController: NavHostController,
    viewModel: CreateCategoryVM = hiltViewModel(),
    categoryType: String?
) {

    val categoryName = remember {
        mutableStateOf("")
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
        ) {
            Box {
                Text(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    text = stringResource(id = R.string.label_add_new_category),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    fontSize = fontDimensionResource(
                        id = R.dimen.text_18
                    ),
                    fontWeight = FontWeight(700)
                )
                IconButton(onClick = {
                    navController.popBackStack()
                }, modifier = Modifier.align(Alignment.CenterEnd)) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        modifier = Modifier.size(dimensionResource(id = R.dimen.offset_26))
                    )
                }
            }
        }

        Text(
            text = stringResource(id = R.string.label_category_name),
            style = TextStyle(
                fontSize = fontDimensionResource(id = R.dimen.text_12),
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.offset_16))
                .clickable {
                    outNavController.navigate("${Screen.ChooseCategoryIcon.route}")
                }
        )

        BasicLowOutlineTextField(
            expenseNameState = categoryName,
            //isError = categoryName.value is ExpenseLogState.NameErrorExpenseLogState,
            errorMessage = stringResource(id = R.string.label_field_is_empty)
        )
    }
}