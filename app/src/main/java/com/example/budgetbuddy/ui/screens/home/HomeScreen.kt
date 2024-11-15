package com.example.budgetbuddy.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.homescreen.BalanceBox
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.elements.transactions.TransactionList
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.White

@Composable
fun HomeScreen(
    navigationRouter: INavigationRouter
) {
    var loading = false

    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val state = viewModel.homeScreenUIState.collectAsState()
    val currency = viewModel.activeCurrency.collectAsState()
    val transactions: MutableList<Transaction> = mutableListOf()

    BaseScreen(
        navigation = navigationRouter,
        showLoading = loading,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigationRouter.navigateToAddEditTransactionScreen(null)
                },
                containerColor = Green,
                contentColor = White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        placeholderScreenContent = null,
        topBar = null
        /*
        placeholderScreenContent = if (transactions.size == 0) {
            PlaceholderScreenContent(
                image = R.drawable.list,
                title = stringResource(id = R.string.list_no_data_title),
                text = stringResource(id = R.string.list_no_data_subtitle)
            )
        } else null*/

    ) {
        HomeScreenContent(
            paddingValues = it,
            actions = viewModel,
            navigationRouter = navigationRouter,
            transactions = transactions,
            currency = currency.value,

        )
    }
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    actions: HomeScreenActions,
    navigationRouter: INavigationRouter,
    transactions: List<Transaction>,
    currency: String
) {

    val updatedCurrency = remember { mutableStateOf(currency) }

    Column {

        BalanceBox(
            currency = updatedCurrency.value,
            onCurrencyChange = { newCurrency ->
                updatedCurrency.value = newCurrency
                actions.changeCurrency(newCurrency)
            }
        )

        Spacer(modifier = Modifier.height(BasicMargin()))

        Box(modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = BasicMargin())) {
            TransactionList(transactions)
        }

    }
}
