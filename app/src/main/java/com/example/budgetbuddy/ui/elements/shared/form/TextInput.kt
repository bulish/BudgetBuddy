package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.budgetbuddy.ui.theme.Grey

@Composable
fun TextInput(
    label: String,
    value: String,
    error: Int?,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        placeholder = { Text(text = label, color = MaterialTheme.colorScheme.secondary) },
        label = { Text(text = label, color = MaterialTheme.colorScheme.secondary) },
        value = value,
        isError = error != null,
        supportingText = {
            InputError(error = error)
        },
        onValueChange = {
            onChange(it)
        },
        modifier = Modifier
            .fillMaxWidth(),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = Grey,
        ),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
    )
}
