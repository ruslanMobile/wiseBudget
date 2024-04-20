package com.finance.presentation.ui.custom_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressBar(
    outModifier: Modifier,
    customModifier: Modifier,
    height: Dp,
    backgroundColor: Color,
    foregroundColor: Brush,
    percent: Int
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Box(
        modifier = outModifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = customModifier
                .background(backgroundColor)
                .width(screenWidth)
                .height(height * 0.5f)
        )
        Box(
            modifier = customModifier
                .background(foregroundColor)
                .width(screenWidth * percent / 100)
                .height(height)
        )
    }
}