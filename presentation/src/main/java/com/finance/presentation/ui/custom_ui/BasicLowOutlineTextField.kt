package com.finance.presentation.ui.custom_ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.finance.presentation.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasicLowOutlineTextField(
    expenseNameState: MutableState<String>,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    BasicTextField(
        value = expenseNameState.value,
        onValueChange = { value ->
            expenseNameState.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(top = dimensionResource(id = R.dimen.offset_4)),
        singleLine = true,
        keyboardOptions = keyboardOptions
    ) { innerTextField ->

        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = expenseNameState.value,
            innerTextField = innerTextField,
            enabled = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            singleLine = true,
            visualTransformation = VisualTransformation.None,

            interactionSource = MutableInteractionSource(),
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 0.dp,
                bottom = 0.dp
            )
        )
    }
}