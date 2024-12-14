package com.example.budgetbuddy.ui.screens.transactions.addEdit

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.CustomAlertDialog
import com.example.budgetbuddy.ui.elements.shared.ShowToast
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

    val currencies = remember {
        mutableStateOf<Map<String, Double>?>(null)
    }

    var selectedCategory by remember {
        mutableStateOf<TransactionCategory?>(TransactionCategory.fromString(data.transaction.category))
    }

    var selectedCurrency = remember {
        mutableStateOf<String?>("")
    }

    var selectedPlace by remember {
        mutableStateOf<Place?>(null)
    }

    var loading = true

    state.value.let {
        when(it){
            AddEditTransactionUIState.Loading -> {
                viewModel.initializeTransactionData(id)
            }

            is AddEditTransactionUIState.TransactionChanged -> {
                data = it.data
                loading = false
            }

            is AddEditTransactionUIState.TransactionSaved -> {
                ShowToast(message = stringResource(id = it.message))
                
                LaunchedEffect(it){
                    navigationRouter.returnBack()
                }
            }

            AddEditTransactionUIState.TransactionDeleted -> {
                LaunchedEffect(it){
                    navigationRouter.navigateToTransactionsListScreen()
                }
            }

            AddEditTransactionUIState.UserNotAuthorized -> {
                navigationRouter.returnBack()
                loading = false
            }

            is AddEditTransactionUIState.Error -> {
                ShowToast(stringResource(id = it.error.communicationError))
                loading = false
            }

            is AddEditTransactionUIState.TransactionDataLoaded -> {
                currencies.value = it.data.currencies.mapValues { entry ->
                    entry.value.toDoubleOrNull() ?: 0.0
                }

                places.clear()
                places.addAll(it.data.places)

                it.data.transaction?.let  {
                    data.transaction = it
                }

                selectedCategory = TransactionCategory.fromString(it.data.transaction?.category)

                val currencyKeys: List<String> = it.data.currencies.keys.toList() ?: emptyList()
                selectedCurrency.value = it.data.transaction?.currency ?: currencyKeys[0]

                selectedPlace = places.find { p -> p.id == it.data.transaction?.placeId }
                loading = false
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
        navigation = navigationRouter,
        showLoading = loading
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
            places = places,
            currencies = currencies.value,
            selectedCategory = selectedCategory,
            changeSelectedCategory = { newValue ->
                selectedCategory = newValue
            },
            selectedCurrency = selectedCurrency.value,
            changeSelectedCurrency = { newValue ->
                selectedCurrency.value = newValue
            },
            selectedPlace = selectedPlace,
            changeSelectedPlace = { newValue ->
                selectedPlace = newValue
            }
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
    places: List<Place>,
    currencies: Map<String, Double>?,
    selectedCategory: TransactionCategory?,
    changeSelectedCategory: (TransactionCategory) -> Unit,
    selectedCurrency: String?,
    changeSelectedCurrency: (String) -> Unit,
    selectedPlace: Place?,
    changeSelectedPlace: (Place) -> Unit,
){
    val currencyKeys: List<String> = currencies?.keys?.toList() ?: emptyList()

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
                        changeSelectedCategory(category)
                        actions.onTransactionCategoryChanged(category)
                    }
                )

                TextInput(
                    label = stringResource(id = R.string.price_label),
                    value = data.transaction.price.toString(),
                    error = data.transactionPriceError,
                    onChange = { actions.onTransactionPriceChange(it.toDouble()) },
                    isNumber = true
                )

                Dropdown(
                    value = selectedCurrency,
                    noValueMessage = stringResource(id = R.string.no_currency_selected),
                    data = currencyKeys,
                    toStringRepresentation = {currency ->
                        currency
                    },
                    onChange = {currency ->
                        changeSelectedCurrency(currency)
                        actions.onTransactionCurrencyChange(currency)
                    }
                )

                CustomRadioButton(
                    label = stringResource(id = R.string.transaction_type_label),
                    value = data.transaction.type,
                    onChange = { actions.onTransactionTypeChange(it) }
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
                        changeSelectedPlace(place)
                        actions.onTransactionPlaceChange(place)
                    }
                )

                TextInput(
                    label = stringResource(id = R.string.note_label),
                    value = data.transaction.note.toString(),
                    error = data.transactionNoteError ,
                    onChange = { actions.onTransactionNoteChange(it) }
                )

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
