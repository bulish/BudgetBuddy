package com.example.budgetbuddy.ui.screens.places.addEditPlace

import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgetbuddy.R
import com.example.budgetbuddy.extensions.getValue
import com.example.budgetbuddy.extensions.removeValue
import com.example.budgetbuddy.extensions.round
import com.example.budgetbuddy.model.Location
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.InfoElement
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.elements.shared.form.TextInput
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPlaceScreen(
    navigationRouter: INavigationRouter,
    id: Long?
){

    val viewModel = hiltViewModel<AddEditPlaceViewModel>()

    val state = viewModel.addEditPlaceUIState.collectAsStateWithLifecycle()

    var data by remember {
        mutableStateOf(AddEditPlaceScreenData())
    }

    LifecycleEventEffect(event = Lifecycle.Event.ON_RESUME) {
        val mapLocationResult = navigationRouter.getNavController().getValue<String>("location")
        mapLocationResult?.value?.let {
            val moshi: Moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<Location> = moshi.adapter(Location::class.java)
            val location = jsonAdapter.fromJson(it)
            navigationRouter.getNavController().removeValue<String>("location")
            location?.let {
                Log.d("latitude", "${it.latitude}")
                viewModel.onLocationChanged(it.latitude, it.longitude)
            }
        }
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
        }
    }

    BaseScreen(
        topBarText = stringResource(
            id = if (id != null) R.string.edit_place_title else R.string.add_place_title
        ),
        onBackClick = {
            navigationRouter.returnBack()
        },
        actions = {
            if (id != null){
                IconButton(onClick = {
                    viewModel.deletePlace()
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
            actions = viewModel
        )
    }
}

@Composable
fun AddEditPlaceScreenContent(
    paddingValues: PaddingValues,
    data: AddEditPlaceScreenData,
    navigationRouter: INavigationRouter,
    actions: AddEditPlaceScreenActions,
){

    Column(modifier = Modifier.padding(paddingValues))
    {
        OutlinedTextField(
            value = data.place.name,
            onValueChange = {
                actions.onPlaceNameChanged(it)
            },
            isError = data.placeNameError != null,
            supportingText = {
                if (data.placeNameError != null){
                    Text(text = stringResource(id = data.placeNameError!!))
                }
            }
        )

        TextInput(
            label = stringResource(id = R.string.name_label),
            value = data.place.name,
            error = data.placeNameError,
            onChange = { actions.onPlaceNameChanged(it) }
        )

        InfoElement(
            icon = Icons.Default.LocationOn,
            value = if (data.place.latitude != null && data.place.longitude != null)
                "${data.place.latitude!!.round()}, ${data.place.longitude!!.round()}"
            else
                null,
            hint = stringResource(id = R.string.location),
            onClick = {
                navigationRouter.navigateToMapScreen(data.place.latitude, data.place.longitude)
            },
            onClearClick = {
                actions.onLocationChanged(null, null)
            })


        Button(onClick = {
            actions.savePlace()
        }) {
            Text(text = "Save place")
        }
    }
}