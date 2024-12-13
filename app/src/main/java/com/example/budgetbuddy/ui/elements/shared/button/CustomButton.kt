package com.example.budgetbuddy.ui.elements.shared.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalFocusManager
import com.example.budgetbuddy.ui.theme.BorderWidth

@Composable
fun CustomButton(
    type: CustomButtonStyle,
    text: String,
    onClickAction: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    OutlinedButton(
        onClick = {
            focusManager.clearFocus()
            onClickAction()
        },
        modifier = type.modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = type.containerColor,
        ),
        border = BorderStroke(BorderWidth(), type.strokeColor)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, color = type.contentColor)
        }
    }
}
