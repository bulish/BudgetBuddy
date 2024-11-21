package com.example.budgetbuddy.ui.elements.map

import com.example.budgetbuddy.R
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.ui.elements.shared.form.TextInput
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AddressInputField(
    onAddressSelected: (String, Double, Double) -> Unit,
    context: Context,
    addressError: Int?
) {
    var address by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedPlaceId by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var isItemSelected by remember { mutableStateOf(false) }

    LaunchedEffect(address) {
        if (address.isNotEmpty() && !isItemSelected) {
            coroutineScope.launch {
                suggestions = getAutocompleteSuggestions(address, context)
            }
        } else if (isItemSelected) {
            isItemSelected = false
        }
    }

    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            TextInput(
                label = stringResource(id = R.string.address),
                value = address,
                error = addressError,
                onChange = { newAddress ->
                    address = newAddress
                }
            )
        }

        if (suggestions.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    suggestions.forEach { suggestion ->
                        Text(
                            suggestion,
                            modifier = Modifier
                                .clickable {
                                    coroutineScope.launch {
                                        val placeId = getPlaceIdFromSuggestion(suggestion, context)
                                        selectedPlaceId = placeId
                                        address = suggestion

                                        selectedPlaceId?.let {
                                            coroutineScope.launch {
                                                val coordinates = getPlaceCoordinates(it, context)
                                                coordinates?.let { (latitude, longitude) ->
                                                    onAddressSelected(suggestion, latitude, longitude)
                                                }
                                            }
                                        }

                                        suggestions = emptyList()
                                        isItemSelected = true
                                    }
                                }
                                .fillMaxWidth()
                                .border(1.dp, Color.LightGray)
                                .padding(horizontal = BasicMargin(), vertical = HalfMargin())
                        )
                    }
                }
            }
        }
    }
}

suspend fun getPlaceIdFromSuggestion(suggestion: String, context: Context): String? {
    val placesClient: PlacesClient = Places.createClient(context)

    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(suggestion)
        .build()

    val response = placesClient.findAutocompletePredictions(request).await()
    return response.autocompletePredictions
        .firstOrNull { it.getPrimaryText(null).toString() == suggestion }
        ?.placeId
}

suspend fun getPlaceCoordinates(placeId: String, context: Context): Pair<Double, Double>? {
    val placesClient: PlacesClient = Places.createClient(context)

    val placeFields = listOf(Place.Field.ID, Place.Field.LAT_LNG)
    val request = FetchPlaceRequest.builder(placeId, placeFields).build()

    val placeResponse = placesClient.fetchPlace(request).await()

    val place = placeResponse.place
    val latLng = place.latLng
    return latLng?.let { Pair(it.latitude, it.longitude) }
}

suspend fun getAutocompleteSuggestions(query: String, context: Context): List<String> {
    val placesClient: PlacesClient = Places.createClient(context)

    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .build()

    val response = placesClient.findAutocompletePredictions(request).await()
    return response.autocompletePredictions.map { it.getPrimaryText(null).toString() }
}
