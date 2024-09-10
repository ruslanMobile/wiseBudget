package com.finance.presentation.ui.categories.category_icon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.finance.presentation.R
import com.finance.presentation.ui.categories.create_category.CreateCategoryVM
import com.finance.presentation.ui.categories.create_category.chooseCategoryIconList
import com.finance.presentation.utils.fontDimensionResource
import com.finance.presentation.utils.header

@Composable
fun ChooseCategoryIconScreen(
    viewModel: CreateCategoryVM = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(5), content = {
            chooseCategoryIconList.forEach { iconCategory ->
                header {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.offset_12),
                            bottom = dimensionResource(id = R.dimen.offset_6)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.offset_1))
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        ) {}
                        Text(
                            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.offset_16)),
                            text = stringResource(id = iconCategory.iconCategory.textOfCategory),
                            style = TextStyle(
                                fontSize = fontDimensionResource(id = R.dimen.text_20),
                                fontFamily = FontFamily(Font(resId = R.font.chakra_petch_medium)),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                        )
                        Box(
                            modifier = Modifier
                                .height(dimensionResource(id = R.dimen.offset_1))
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                        ) {}
                    }
                }
                items(iconCategory.iconsList.size) { index ->
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.offset_8)))
                                .border(
                                    width = dimensionResource(id = R.dimen.offset_1),
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.offset_8))
                                )
                                .padding(
                                    vertical = dimensionResource(id = R.dimen.offset_6),
                                    horizontal = dimensionResource(id = R.dimen.offset_6)
                                )
                                .clickable {

                                }
                        ) {
                            Icon(
                                painter = painterResource(id = iconCategory.iconsList[index]),
                                modifier = Modifier.size(dimensionResource(id = R.dimen.offset_26)),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        })
    }
}