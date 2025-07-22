package com.nikol.account.screens.edit.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign

@Composable
internal fun AlwaysEditableTextField(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    onTextChanged: (String) -> Unit,
) {
    var currentText by remember { mutableStateOf(text) }
    LaunchedEffect(text) {
        if (text != currentText) {
            currentText = text
        }
    }

    val textColor = MaterialTheme.colorScheme.onBackground

    BasicTextField(
        value = currentText,
        onValueChange = {
            currentText = it
            onTextChanged(it)
        },
        singleLine = true,
        textStyle = textStyle.copy(color = textColor),
        modifier = modifier
    )
}
@Composable
internal fun FormattedAmountTextField(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    onTextChanged: (String) -> Unit
) {
    val context = LocalContext.current

    var currentTextFieldValue by remember {
        mutableStateOf(TextFieldValue(text = text, selection = TextRange(text.length)))
    }

    LaunchedEffect(text) {
        if (text != currentTextFieldValue.text) {
            currentTextFieldValue = TextFieldValue(
                text = text,
                selection = TextRange(text.length)
            )
        }
    }

    val textColor = MaterialTheme.colorScheme.onBackground

    BasicTextField(
        value = currentTextFieldValue,
        onValueChange = { newValue ->
            val digitsOnly = newValue.text.filter { it.isDigit() }
            val isTooLong = digitsOnly.length > 10
            val isTooBig = digitsOnly.toLongOrNull()?.let { it > Int.MAX_VALUE } ?: false

            if (isTooLong || isTooBig) {
                Toast.makeText(
                    context,
                    "Невозможно ввести больше 10 цифр",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                currentTextFieldValue = TextFieldValue(
                    text = digitsOnly,
                    selection = TextRange(digitsOnly.length)
                )
                onTextChanged(digitsOnly)
            }
        },
        singleLine = true,
        textStyle = textStyle.copy(
            textAlign = TextAlign.End,
            color = textColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}
