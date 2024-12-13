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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableDoubleStateOf
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

    val transactionSum = remember {
        mutableDoubleStateOf(0.0)
    }

    LaunchedEffect(Unit) {
        viewModel.loadTransactions()
    }

    state.value.let {
        when (it) {
            is HomeScreenUIState.Loading -> {
                viewModel.loadTransactions()
            }

            is HomeScreenUIState.Success -> {
                loading = false
                transactions.clear()
                transactions.addAll(it.transactions)
                transactionSum.doubleValue = it.totalSum
            }

            is HomeScreenUIState.UserNotAuthorized -> {
                navigationRouter.navigateToLoginScreen()
            }
        }
    }

    BaseScreen(
        navigation = navigationRouter,
        showLoading = loading,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigationRouter.navigateToAddEditTransactionScreen(null)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        placeholderScreenContent = null,
        topBar = null
    ) {
        HomeScreenContent(
            paddingValues = it,
            actions = viewModel,
            navigationRouter = navigationRouter,
            transactions = transactions,
            currency = currency.value,
            sum = transactionSum.doubleValue
        )
    }
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    actions: HomeScreenActions,
    navigationRouter: INavigationRouter,
    transactions: List<Transaction>,
    currency: String,
    sum: Double
) {
    val updatedCurrency = remember { mutableStateOf(currency) }

    Column {

        BalanceBox(
            currency = updatedCurrency.value,
            onCurrencyChange = { newCurrency ->
                updatedCurrency.value = newCurrency
                actions.changeCurrency(newCurrency)
            },
            sum = sum,
            addNewTransaction = {
                navigationRouter.navigateToAddEditTransactionScreen(null)
            }
        )

        Spacer(modifier = Modifier.height(BasicMargin()))

        Box(modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = BasicMargin())) {
            TransactionList(
                displayTitle = true,
                transactions = transactions,
                navigation = navigationRouter
            )
        }
    }
}
