package com.example.budgetbuddy.ui.elements.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.HalfMargin

@Composable
fun TransactionList(transactions: List<Transaction>) {
    Column {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = HalfMargin())
        )

        if (transactions.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(DoubleMargin()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = stringResource(id = R.string.list_no_data_title),
                    style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.list_no_data_subtitle),
                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.secondary),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(BasicMargin()))

                Button(onClick = { /* Maybe add a transaction action */ }, modifier = Modifier.align(
                    Alignment.CenterHorizontally)) {
                    Text(text = "Add Transaction", color = MaterialTheme.colorScheme.tertiary)
                }
            }
        } else {
            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}
