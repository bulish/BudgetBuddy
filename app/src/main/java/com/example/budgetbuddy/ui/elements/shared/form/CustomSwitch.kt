package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import com.example.budgetbuddy.ui.theme.White

@Composable
fun CustomSwitch(
    value: Boolean,
    onChange: (Boolean) -> Unit
) {

    Switch(
        checked = value,
        onCheckedChange = {
            onChange(value)
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = White,
            uncheckedThumbColor = MaterialTheme.colorScheme.tertiary,
        )
    )
}
