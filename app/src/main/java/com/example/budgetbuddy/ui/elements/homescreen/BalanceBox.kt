package com.example.budgetbuddy.ui.elements.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgetbuddy.extensions.toFormattedString
import com.example.budgetbuddy.ui.elements.shared.CurrencyDropdown
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.TripleMargin
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.screens.home.TestTagHomeScreenAddMoneyButton

@Composable
fun BalanceBox(
    currency: String,
    onCurrencyChange: (String) -> Unit,
    sum: Double,
    addNewTransaction: () -> Unit,
    currencies: Map<String, Double>?
) {

    val updatedCurrency = remember { mutableStateOf(currency) }

    LaunchedEffect(currency) {
        updatedCurrency.value = currency
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = DoubleMargin(), vertical = TripleMargin()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CurrencyDropdown(
            currency = updatedCurrency.value,
            onChange = { newCurrency ->
                onCurrencyChange(newCurrency)
            },
            currencies = currencies,
            testTag = ""
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${sum.toFormattedString()} ${updatedCurrency.value}",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(id = R.string.available_balance),
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { addNewTransaction() },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            modifier = Modifier.testTag(TestTagHomeScreenAddMoneyButton)
        ) {
            Text(text = stringResource(id = R.string.add_new_money), color = MaterialTheme.colorScheme.primary)
        }
    }
}
