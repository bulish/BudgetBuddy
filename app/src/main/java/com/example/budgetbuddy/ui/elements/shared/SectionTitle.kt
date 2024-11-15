package com.example.budgetbuddy.ui.elements.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.QuarterMargin

@Composable
fun SectionTitle(
    title: String,
    amount: Int? = null,
    horizontalPadding: Dp = BasicMargin(),
    showDivider: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = QuarterMargin()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                color = Green
            )

            if (amount != null) {
                Text(
                    text = amount.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = HalfMargin())
                )
            }

        }

        if (showDivider) CustomDivider()
    }
}
