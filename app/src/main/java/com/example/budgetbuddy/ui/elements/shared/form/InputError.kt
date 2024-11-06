package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun InputError(error: Int?) {
    if (error != null) {
        Text(text = stringResource(id = error), color = Color.Red, fontSize = 12.sp)
    }
}
