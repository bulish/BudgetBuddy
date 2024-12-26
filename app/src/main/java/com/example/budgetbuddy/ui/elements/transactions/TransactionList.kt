package com.example.budgetbuddy.ui.elements.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.screens.transactions.list.TransactionEmptyList
import com.example.budgetbuddy.ui.screens.transactions.list.TransactionLazyList
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.White

const val TestTagListOfTransactionsScreenListContainer = "TestTagListOfTransactionsScreenListContainer"

@Composable
fun TransactionList(
    displayTitle: Boolean = true,
    transactions: List<Transaction>,
    navigation: INavigationRouter,
    transformPrice: (Transaction) -> String
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .testTag(TestTagListOfTransactionsScreenListContainer)
    ) {
        if (displayTitle) {
            Text(
                text = stringResource(id = R.string.transactions),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(vertical = HalfMargin())
            )
        }

        if (transactions.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(HalfMargin()))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(DoubleMargin())
                    .testTag(TransactionEmptyList),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = stringResource(id = R.string.list_no_data_title),
                    style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.padding(bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(id = R.string.list_no_data_subtitle),
                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.secondary),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(BasicMargin()))

                Button(onClick = { navigation.navigateToAddEditTransactionScreen(null) }, modifier = Modifier.align(
                    Alignment.CenterHorizontally)) {
                    Text(text = stringResource(id = R.string.add_transaction), color = White)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.testTag(TransactionLazyList),
                verticalArrangement = Arrangement.spacedBy(BasicMargin())) {
                itemsIndexed(transactions) { index, transaction ->
                    TransactionItem(
                        transaction,
                        navigation,
                        transformPrice = {
                            transformPrice(transaction)
                        },
                        index
                    )
                }
            }
        }
    }
}
