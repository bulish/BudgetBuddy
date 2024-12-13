package com.example.budgetbuddy.ui.screens.places.addEditPlace

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.PlaceCategory
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.map.AddressInputField
import com.example.budgetbuddy.ui.elements.map.PlaceIcon
import com.example.budgetbuddy.ui.elements.shared.CustomAlertDialog
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.elements.shared.form.Dropdown
import com.example.budgetbuddy.ui.elements.shared.form.ImageInput
import com.example.budgetbuddy.ui.elements.shared.form.SaveCancelButtons
import com.example.budgetbuddy.ui.elements.shared.form.TextInput
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.HalfMargin
import java.io.File

@Composable
fun AddEditPlaceScreen(
    navigationRouter: INavigationRouter,
    context: Context,
    id: Long?
){

    val viewModel = hiltViewModel<AddEditPlaceViewModel>()

    val state = viewModel.addEditPlaceUIState.collectAsStateWithLifecycle()

    var data by remember {
        mutableStateOf(AddEditPlaceScreenData())
    }

    var dialogIsVisible by remember {
        mutableStateOf<Boolean>(false)
    }

    state.value.let {
        when(it){
            AddEditPlaceUIState.Loading -> {
                viewModel.loadPlace(id)
            }
            is AddEditPlaceUIState.PlaceChanged -> {
                data = it.data
            }
            AddEditPlaceUIState.PlaceSaved -> {
                LaunchedEffect(it){
                    navigationRouter.returnBack()
                }
            }
            AddEditPlaceUIState.PlaceDeleted -> {
                LaunchedEffect(it){
                    navigationRouter.returnBack()
                }
            }
            AddEditPlaceUIState.UserNotAuthorized -> {
                navigationRouter.returnBack()
            }
        }
    }

    BaseScreen(
        topBarText = stringResource(
            id = if (id != null) R.string.edit_place_title else R.string.add_place_title
        ),
        hideNavigation = true,
        onBackClick = {
            dialogIsVisible = true
        },
        actions = {
            if (id != null){
                IconButton(onClick = {
                    viewModel.deletePlace()
                    navigationRouter.returnBack()
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }
        },
        navigation = navigationRouter
    ) {
        AddEditPlaceScreenContent(
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
            }

        )
    }
}

@Composable
fun AddEditPlaceScreenContent(
    paddingValues: PaddingValues,
    data: AddEditPlaceScreenData,
    navigationRouter: INavigationRouter,
    actions: AddEditPlaceScreenActions,
    onCancel: () -> Unit,
    context: Context,
    dialogIsVisible: Boolean,
    onDialogDismiss: () -> Unit,
    onDialogConfirmation: () -> Unit

){
    var selectedCategory by remember { mutableStateOf<PlaceCategory?>(data.place.category) }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    LaunchedEffect(data.place.imageName) {
        if (data.place.imageName != null) {
            val file = File(context.filesDir, data.place.imageName)
            selectedImageUri = Uri.fromFile(file)
        } else {
            selectedImageUri = null
        }
    }

    LazyColumn(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = BasicMargin())
    )
    {

        item {
            ImageInput(
                onChangeHandler = {
                    actions.onPlaceImageChanged(it)
                },
                context = context,
                selectedImageUri = selectedImageUri,
                changeSelectedImageUri = {
                    selectedImageUri = it
                }
            )

            PlaceIcon(
                filesDir = context.filesDir,
                iconName = data.place.imageName,
                onClickHandler = {
                    selectedImageUri = null
                    data.place.imageName = null
                }
            )

            TextInput(
                label = stringResource(id = R.string.name_label),
                value = data.place.name,
                error = data.placeNameError,
                onChange = { actions.onPlaceNameChanged(it) }
            )

            Dropdown(
                value = selectedCategory,
                noValueMessage = stringResource(id = R.string.no_category_selected),
                data = PlaceCategory.entries,
                toStringRepresentation = {category ->
                    stringResource(id = category.getStringResource())
                },
                onChange = {category ->
                    selectedCategory = category
                    actions.onPlaceCategoryChanged(category)
                }
            )

            Spacer(modifier = Modifier.height(HalfMargin()))

            AddressInputField(
                onAddressSelected = {address, lat, long ->
                    actions.onPlaceAddressChanged(address)
                    actions.onLocationChanged(lat, long)
                },
                context = context,
                addressError = data.placeAddressError,
                address = data.place.address,
                onAddressChange = { address ->
                    actions.onPlaceAddressChanged(address)
                }
            )

            SaveCancelButtons(
                onCancel = { onCancel() },
                onSave = { actions.savePlace() }
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