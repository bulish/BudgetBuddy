package com.example.budgetbuddy.ui.screens.transactions.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.elements.shared.placeholderScreen.PlaceholderScreenContent
import com.example.budgetbuddy.ui.elements.transactions.TransactionList
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.QuarterMargin
import com.example.budgetbuddy.ui.theme.White

@Composable
fun TransactionsListScreen(
    navigationRouter: INavigationRouter
) {
    val viewModel = hiltViewModel<TransactionsListViewModel>()

    val transactions = remember {
        mutableListOf<Transaction>()
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    var loading = true

    state.value.let {
        when (it) {
            is TransactionsListUIState.DataLoaded -> {
                transactions.addAll(it.data)
            }

            TransactionsListUIState.Loading -> {

            }

            TransactionsListUIState.UserNotAuthorized -> {
                loading = true
                navigationRouter.navigateToLoginScreen()
            }

        }
    }
    BaseScreen(
        topBar = null,
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
        topBarText = stringResource(id = R.string.transactions),
        navigation = navigationRouter,
        placeholderScreenContent = if (transactions.size == 0) {
        PlaceholderScreenContent(
            image = R.drawable.list,
            title = stringResource(id = R.string.list_no_data_title),
            text = stringResource(id = R.string.list_no_data_subtitle)
        )
} else null
    ) {
        TransactionsListScreenContent(
            transactions = transactions,
            paddingValues = it
        )
    }
}

@Composable
fun TransactionsListScreenContent(
    transactions: List<Transaction>,
    paddingValues: PaddingValues
) {
    var selectedFilter by remember { mutableStateOf(TransactionCategory.SALARY) }
    val filters = TransactionCategory.entries
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = BasicMargin())) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    dropdownExpanded = true
                }
        ) {
            Text(
                text = selectedFilter.value,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.tertiary)
        ) {
            filters.forEach { filter ->
                DropdownMenuItem(onClick = {
                    selectedFilter = filter
                    dropdownExpanded = false
                },  text = {
                    Text(text = filter.value)
                })
            }
        }

        Text(
            text = "Sum: -2,565 Kč",
            modifier = Modifier.padding(BasicMargin()),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = BasicMargin())
        ) {
            TransactionList(
                displayTitle = false,
                transactions = transactions
            )
        }
    }
}
