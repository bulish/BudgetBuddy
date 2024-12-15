package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.theme.Grey

@Composable
fun PasswordInput(
    value: String,
    onChange: (password: String) -> Unit,
    error: Int?,
    testTag: String
) {
    OutlinedTextField(
        placeholder = {
            Text(
                text = stringResource(id = R.string.placeholder_password),
                color = MaterialTheme.colorScheme.secondary
            )
        },
        label = {
            Text(
                text = stringResource(id = R.string.placeholder_password),
                color = MaterialTheme.colorScheme.secondary
            )
        },
        value = value,
        isError = error != null,
        onValueChange = {
            onChange(it)
        },
        maxLines = 1,
        modifier = Modifier.fillMaxWidth().testTag(testTag),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = Grey,
        ),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
        trailingIcon = {
            if (error != null) {
                InputError(error = error)
            }
        }
    )
}

