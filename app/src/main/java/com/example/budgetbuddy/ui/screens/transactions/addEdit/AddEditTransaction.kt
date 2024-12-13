package com.example.budgetbuddy.ui.screens.transactions.addEdit

import android.content.Context
import android.net.Uri
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.CustomAlertDialog
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.elements.shared.form.CustomDatePicker
import com.example.budgetbuddy.ui.elements.shared.form.CustomRadioButton
import com.example.budgetbuddy.ui.elements.shared.form.Dropdown
import com.example.budgetbuddy.ui.elements.shared.form.ImageInput
import com.example.budgetbuddy.ui.elements.shared.form.SaveCancelButtons
import com.example.budgetbuddy.ui.elements.shared.form.TextInput
import com.example.budgetbuddy.ui.theme.BasicMargin

@Composable
fun AddEditTransactionScreen(
    navigationRouter: INavigationRouter,
    context: Context,
    id: Long?
){

    val viewModel = hiltViewModel<AddEditTransactionViewModel>()

    val state = viewModel.addEditTransactionUIState.collectAsStateWithLifecycle()

    var data by remember {
        mutableStateOf(AddEditTransactionScreenData())
    }

    val places = remember {
        mutableListOf<Place>()
    }

    var dialogIsVisible by remember {
        mutableStateOf<Boolean>(false)
    }

    state.value.let {
        when(it){
            AddEditTransactionUIState.Loading -> {
                viewModel.loadTransaction(id)
                viewModel.loadPlaces()
            }
            is AddEditTransactionUIState.TransactionChanged -> {
                data = it.data
            }
            AddEditTransactionUIState.TransactionSaved -> {
                LaunchedEffect(it){
                    navigationRouter.returnBack()
                }
            }
            AddEditTransactionUIState.TransactionDeleted -> {
                LaunchedEffect(it){
                    navigationRouter.returnBack()
                }
            }
            AddEditTransactionUIState.UserNotAuthorized -> {
                navigationRouter.returnBack()
            }

            is AddEditTransactionUIState.PlacesLoaded -> {
                places.clear()
                places.addAll(it.data)
            }
        }
    }

    BaseScreen(
        topBarText = stringResource(
            id = if (id != null) R.string.edit_transaction_title else R.string.add_transaction_title
        ),
        hideNavigation = true,
        onBackClick = {
            dialogIsVisible = true
        },
        actions = {
            if (id != null){
                IconButton(onClick = {
                    viewModel.deleteTransaction()
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        },
        navigation = navigationRouter
    ) {
        AddEditTransactionContent(
            paddingValues = it,
            data = data,
            navigationRouter = navigationRouter,
            actions = viewModel,
            onCancel = { dialogIsVisible = true },
            context = context,
            dialogIsVisible = dialogIsVisible,
            onDialogDismiss = { dialogIsVisible = false },
            onDialogConfirmation = {
                dialogIsVisible = false
                navigationRouter.returnBack()
            },
            places = places
        )
    }
}

@Composable
fun AddEditTransactionContent(
    paddingValues: PaddingValues,
    data: AddEditTransactionScreenData,
    navigationRouter: INavigationRouter,
    actions: AddEditTransactionActions,
    onCancel: () -> Unit,
    context: Context,
    dialogIsVisible: Boolean,
    onDialogDismiss: () -> Unit,
    onDialogConfirmation: () -> Unit,
    places: List<Place>
){
    val currencies = listOf("CZK (Kč)", "USD ($)", "EUR (€)", "GBP (£)")

    var selectedCategory by remember {
        mutableStateOf<TransactionCategory?>(data.transaction.category)
    }

    var selectedCurrency by remember {
        mutableStateOf<String?>(currencies[0])
    }

    var selectedPlace by remember {
        mutableStateOf<Place?>(places.find { p -> p.id == data.transaction.placeId })
    }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    LaunchedEffect(data.transactionPriceError) {
        selectedImageUri = null
    }

    LazyColumn(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = BasicMargin()),
    )
    {

        item {
            Column(verticalArrangement = Arrangement.spacedBy(BasicMargin()),
                modifier = Modifier.fillMaxSize()) {
                ImageInput(
                    onChangeHandler = {
                        actions.onReceiptChange()
                    },
                    context = context,
                    selectedImageUri = selectedImageUri,
                    changeSelectedImageUri = {
                        selectedImageUri = it
                    }
                )

                Dropdown(
                    value = selectedCategory,
                    noValueMessage = stringResource(id = R.string.no_category_selected),
                    data = TransactionCategory.entries,
                    toStringRepresentation = {category ->
                        stringResource(id = category.getStringResource())
                    },
                    onChange = {category ->
                        selectedCategory = category
                        actions.onTransactionCategoryChanged(category)
                    }
                )

                //Spacer(modifier = Modifier.height((-8).dp))

                TextInput(
                    label = stringResource(id = R.string.price_label),
                    value = data.transaction.price.toString(),
                    error = data.transactionPriceError,
                    onChange = { actions.onTransactionPriceChange(it.toDouble()) },
                    isNumber = true
                )

                TextInput(
                    label = stringResource(id = R.string.note_label),
                    value = data.transaction.note.toString(),
                    error = data.transactionNoteError ,
                    onChange = { actions.onTransactionNoteChange(it) }
                )

                CustomRadioButton(
                    label = stringResource(id = R.string.transaction_type_label),
                    value = data.transaction.type,
                    onChange = { actions.onTransactionTypeChange(it) }
                )

                Dropdown(
                    value = selectedCurrency,
                    noValueMessage = stringResource(id = R.string.no_currency_selected),
                    data = currencies,
                    toStringRepresentation = {currency ->
                        currency.toString()
                    },
                    onChange = {currency ->
                        selectedCurrency = currency.toString()
                        actions.onTransactionCurrencyChange(currency.toString())
                    }
                )

                CustomDatePicker(
                    value = data.transaction.date,
                    onChange = { actions.onTransactionDateChange(it) }
                )

                Dropdown(
                    value = selectedPlace,
                    noValueMessage = stringResource(id = R.string.no_place_selected),
                    data = places,
                    toStringRepresentation = {place ->
                        place.name
                    },
                    onChange = {place ->
                        selectedPlace = place
                        actions.onTransactionPlaceChange(place)
                    }
                )

                //Spacer(modifier = Modifier.height(BasicMargin()))

                SaveCancelButtons(
                    onCancel = { onCancel() },
                    onSave = { actions.saveTransaction() }
                )

                if (dialogIsVisible) {
                    CustomAlertDialog(
                        onDismissRequest = { onDialogDismiss() },
                        onConfirmation = { onDialogConfirmation() },
                        dialogTitle = stringResource(id = R.string.alert_dialog_add_edit_title) ,
                        dialogText = stringResource(id = R.string.alert_dialog_add_edit_subtitle),
                        icon = Icons.Default.Delete
                    )
                }
            }
        }

    }
}