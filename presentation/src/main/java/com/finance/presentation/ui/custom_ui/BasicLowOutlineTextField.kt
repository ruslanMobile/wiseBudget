package com.finance.presentation.ui.custom_ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.finance.presentation.R
import com.finance.presentation.utils.fontDimensionResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasicLowOutlineTextField(
    expenseNameState: MutableState<String>,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    Column() {
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

        if(isError) {
            Text(
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.offset_6)),
                text = errorMessage,
                style = TextStyle(
                    fontSize = fontDimensionResource(id = R.dimen.text_12),
                    color = Color.Black,
                    fontFamily = FontFamily(Font(resId = R.font.chakra_petch_regular)),
                    textAlign = TextAlign.Start
                ),
            )
        }
    }
}