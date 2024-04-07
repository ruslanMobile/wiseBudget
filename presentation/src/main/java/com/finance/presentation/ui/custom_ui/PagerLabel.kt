package com.finance.presentation.ui.custom_ui

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.finance.presentation.R
import com.finance.presentation.utils.fontDimensionResource

@Composable
fun PagerLabel(
    text: String,
    isSelected: Boolean,
    animVal: Float,
    onClickListener: () -> Unit
) {
    val bottomBorder =
        dimensionResource(id = R.dimen.offset_4).value
    val drawLineColor = MaterialTheme.colorScheme.onSurface

    Text(
        text = text,
        fontWeight = if (isSelected) FontWeight(
            700
        ) else FontWeight(500),
        style = TextStyle(
            fontSize = fontDimensionResource(id = R.dimen.text_20),
            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier
            .drawBehind {
                if (isSelected) {
                    drawLine(
                        color = drawLineColor,
                        alpha = animVal,
                        start = Offset(
                            0f,
                            size.height
                        ),
                        end = Offset(animVal * size.width, size.height),
                        strokeWidth = bottomBorder,
                        cap = StrokeCap.Round,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(25f, 10f))
                    )
                }
            }
            .clickable {
                onClickListener.invoke()
            }
    )
}