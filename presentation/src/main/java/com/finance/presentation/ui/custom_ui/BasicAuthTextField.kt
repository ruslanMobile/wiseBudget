package com.finance.presentation.ui.custom_ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.finance.presentation.R
import com.finance.presentation.ui.theme.GreenDark2
import com.finance.presentation.ui.theme.Silver
import com.finance.presentation.utils.fontDimensionResource

@Composable
fun BasicAuthTextField(
    state: MutableState<String>,
    startIcon: Int,
    modifier: Modifier,
    hintText: Int,
    borderBrush: Brush
) {
    BasicTextField(
        value = state.value,
        singleLine = true,
        modifier = modifier,
        cursorBrush = Brush.verticalGradient(
            listOf(Color.White, Color.White)
        ),
        textStyle = TextStyle(color = Color.White, fontSize = fontDimensionResource(id = R.dimen.text_16)),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(
                        brush = borderBrush,
                        width = dimensionResource(id = R.dimen.offset_1),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.offset_12))
                    )
                    .padding(dimensionResource(id = R.dimen.offset_12))
            ) {
                Icon(
                    painter = painterResource(id = startIcon),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.offset_20)),
                    contentDescription = "",
                    tint = Silver
                )
                Spacer(modifier = Modifier.padding(3.dp))
                if (state.value.isEmpty())
                    Text(text = stringResource(id = hintText))
                innerTextField()
            }
        },
        onValueChange = { value ->
            state.value = value
        })
}