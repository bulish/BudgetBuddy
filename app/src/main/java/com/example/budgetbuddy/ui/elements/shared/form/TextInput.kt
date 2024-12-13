package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.ui.theme.Grey
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.Pink40

@Composable
fun TextInput(
    label: String,
    value: String,
    error: Int?,
    onChange: (String) -> Unit,
    isNumber: Boolean = false
) {
    OutlinedTextField(
        placeholder = if (value.isEmpty()) {
            { Text(text = label, color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)) }
        } else null,
        label = { Text(text = label, color = MaterialTheme.colorScheme.secondary) },
        value = value,
        isError = error != null,
        onValueChange = {
            onChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Grey,
            unfocusedBorderColor = Grey
        ),
        keyboardOptions = if (isNumber) {
            KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        } else {
            KeyboardOptions.Default
        },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
        trailingIcon = {
            if (error != null) {
                InputError(error = error)
            }
        }
    )
}
