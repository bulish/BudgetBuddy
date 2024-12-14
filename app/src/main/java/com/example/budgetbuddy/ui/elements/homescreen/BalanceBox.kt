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
import com.example.budgetbuddy.extensions.toFormattedString
import com.example.budgetbuddy.ui.elements.shared.CurrencyDropdown
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.TripleMargin

@Composable
fun BalanceBox(
    currency: String,
    onCurrencyChange: (String) -> Unit,
    sum: Double,
    addNewTransaction: () -> Unit,
    currencies: Map<String, Double>?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = DoubleMargin(), vertical = TripleMargin()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CurrencyDropdown(
            currency = currency,
            onChange = { newCurrency ->
                onCurrencyChange(newCurrency)
            },
            currencies = currencies
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${sum.toFormattedString()} Kč",
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
            onClick = { addNewTransaction() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(text = "Add Money", color = MaterialTheme.colorScheme.primary)
        }
    }
}
