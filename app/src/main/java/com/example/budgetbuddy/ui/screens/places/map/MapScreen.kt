package com.example.budgetbuddy.ui.screens.places.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.White
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navigationRouter: INavigationRouter,
    latitude: Double?,
    longitude: Double?
){

    val viewModel = hiltViewModel<MapViewModel>()

    BaseScreen(
        topBar = null,
        actions = {
            IconButton(onClick = {
                if (viewModel.locationChanged){
                    navigationRouter.returnFromMapScreen(viewModel.latitude!!, viewModel.longitude!!)
                } else {
                    navigationRouter.returnBack()
                }
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigationRouter.navigateToAddEditPlaceScreen(null)
                },
                containerColor = Green,
                contentColor = White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        navigation = navigationRouter
    ) {
        MapScreenContent(
            paddingValues = it,
            actions = viewModel,
            latitude = if (latitude != null) latitude else 49.0,
            longitude = longitude ?: 16.0,
            navigation = navigationRouter
        )
    }
}

@Composable
fun MapScreenContent(
    paddingValues: PaddingValues,
    actions: MapViewModel,
    latitude: Double,
    longitude: Double,
    navigation: INavigationRouter
){

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(latitude, longitude), 10.0f)
    }

    val markerState = rememberMarkerState(position = LatLng(latitude, longitude))

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)){

        GoogleMap (
            cameraPositionState = cameraPositionState
        ) {

            MapEffect {
                it.setOnMarkerDragListener(object : OnMarkerDragListener {
                    override fun onMarkerDrag(p0: Marker) {

                    }

                    override fun onMarkerDragEnd(p0: Marker) {
                        actions.onLocationChanged(p0.position.latitude, p0.position.longitude)
                        navigation.returnBack()
                    }

                    override fun onMarkerDragStart(p0: Marker) {

                    }
                })
            }

            Marker(
                state = markerState,
                draggable = true
            )

        }

    }
}