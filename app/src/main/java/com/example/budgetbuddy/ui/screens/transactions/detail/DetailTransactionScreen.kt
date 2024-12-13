package com.example.budgetbuddy.ui.screens.transactions.detail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetbuddy.R
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.CustomAlertDialog
import com.example.budgetbuddy.ui.elements.shared.SectionTitle
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.elements.shared.button.CustomButton
import com.example.budgetbuddy.ui.elements.shared.button.CustomButtonType
import com.example.budgetbuddy.ui.elements.shared.button.getCustomButtonType
import com.example.budgetbuddy.ui.elements.shared.labeledelement.LabeledElement
import com.example.budgetbuddy.ui.elements.shared.labeledelement.LabeledElementData
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.White
import java.io.File

@Composable
fun DetailTransactionScreen(
    navigationRouter: INavigationRouter,
    id: Long?,
) {
    val viewModel = hiltViewModel<DetailTransactionViewModel>()
    val state = viewModel.detailUIState.collectAsState()

    val data: MutableList<LabeledElementData> = mutableListOf()

    var loading by remember { mutableStateOf(false) }

    state.value.let {
        when (it) {
            DetailTransactionUIState.Loading -> {
                loading = true
                Log.d("detail id", "$id")
                if (id != null) {
                    viewModel.loadTransaction(id)
                }
            }

            DetailTransactionUIState.TransactionDeleted -> {
                navigationRouter.navigateToTransactionsListScreen()
            }

            DetailTransactionUIState.UserNotAuthorized -> {
                loading = true
                navigationRouter.navigateToLoginScreen()
            }

            is DetailTransactionUIState.Success -> {
                Log.d("success data", "$it.data")
                data.addAll(it.data)
                loading = false
            }
        }
    }

    BaseScreen(
        topBarText = "Detail",
        onBackClick = { navigationRouter.returnBack() },
        hideNavigation = true,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigationRouter.navigateToAddEditTransactionScreen(id)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = White
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        },
        navigation = navigationRouter
    ) {
        DetailScreenContent(
            paddingValues = it,
            data = data,
            actions = viewModel,
            id = id
        )
    }
}

@Composable
fun DetailScreenContent(
    paddingValues: PaddingValues,
    data: List<LabeledElementData>,
    actions: DetailTransactionAction,
    id: Long?,
) {
    var dialogIsVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = HalfMargin())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        item {
            SectionTitle(title = stringResource(id = R.string.detail_subtitle))

            Column(modifier = Modifier.padding(vertical = BasicMargin()),
                verticalArrangement = Arrangement.spacedBy(BasicMargin())) {
                data.forEach {
                    LabeledElement(it)
                }
            }

            CustomButton(
                type = getCustomButtonType(buttonType = CustomButtonType.OutlinedMaxSize),
                text = stringResource(id = R.string.detail_delete),
                onClickAction = { dialogIsVisible = true }
            )

            if (dialogIsVisible) {
                CustomAlertDialog(
                    onDismissRequest = { dialogIsVisible = false },
                    onConfirmation = {
                        actions.deleteTransaction(id)
                        dialogIsVisible = false
                    },
                    dialogTitle = stringResource(id = R.string.alert_dialog_delete_title) ,
                    dialogText = stringResource(id = R.string.alert_dialog_delete_subtitle),
                    icon = Icons.Default.Delete
                )
            }
        }
    }
}
