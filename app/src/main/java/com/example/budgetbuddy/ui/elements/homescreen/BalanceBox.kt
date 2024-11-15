package com.example.budgetbuddy.ui.elements.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetbuddy.ui.elements.shared.CurrencyDropdown
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.TripleMargin
import com.example.budgetbuddy.ui.theme.White

@Composable
fun BalanceBox(
    currency: String,
    onCurrencyChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Green)
            .padding(horizontal = DoubleMargin(), vertical = TripleMargin()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CurrencyDropdown(
            currency = currency,
            onChange = { newCurrency ->
                onCurrencyChange(newCurrency)
            }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "5 562 Kč",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Available balance",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Add Money action */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(text = "Add Money", color = Green)
        }
    }
}
