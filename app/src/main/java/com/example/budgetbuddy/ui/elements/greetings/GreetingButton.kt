package com.example.budgetbuddy.ui.elements.greetings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.ui.theme.White

@Composable
fun GreetingButton(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12),
        colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.primary),
        onClick = {
            onClick()
        }) {
        Text(
            text = title,
            color = White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
