package com.example.budgetbuddy.ui.screens.transactions.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
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
import com.example.budgetbuddy.ui.theme.HalfMargin
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

    val transactionSum = remember {
        mutableDoubleStateOf(0.0)
    }

    LaunchedEffect(Unit) {
        viewModel.getAllTransactions()
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    var loading = true

    state.value.let {
        when (it) {
            is TransactionsListUIState.DataLoaded -> {
                transactions.clear()
                transactions.addAll(it.data)
                transactionSum.doubleValue = it.sum
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
            paddingValues = it,
            sum = transactionSum.doubleValue,
            navigation = navigationRouter
        )
    }
}

@Composable
fun TransactionsListScreenContent(
    transactions: List<Transaction>,
    paddingValues: PaddingValues,
    sum: Double,
    navigation: INavigationRouter
) {
    var selectedFilter by remember { mutableIntStateOf(TransactionCategory.SALARY.getStringResource()) }
    val filters = TransactionCategory.entries
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = BasicMargin())
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    dropdownExpanded = true
                }
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(BasicMargin())
        ) {
            Text(
                text = stringResource(id = selectedFilter),
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
            modifier = Modifier
                .width(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.tertiary)
        ) {
            filters.forEach { filter ->
                DropdownMenuItem(onClick = {
                    selectedFilter = filter.getStringResource()
                    dropdownExpanded = false
                },  text = {
                    Text(text = stringResource(id = filter.getStringResource()))
                })
            }
        }

        Row(modifier = Modifier
            .padding(vertical = BasicMargin())
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Sum:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "$sum Kč",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        TransactionList(
            displayTitle = false,
            transactions = transactions,
            navigation = navigation
        )
    }
}
