package com.example.budgetbuddy.ui.screens.places.map

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.map.CustomMapRenderer
import com.example.budgetbuddy.ui.elements.map.PlaceDetail
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.theme.White
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.GridBasedAlgorithm
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    navigationRouter: INavigationRouter,
){
    val viewModel = hiltViewModel<MapViewModel>()

    val mapData = remember {
        mutableListOf<Place>()
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    var loading = true

    LaunchedEffect(Unit) {
        viewModel.loadPlaces()
    }

    state.value.let {
        when(it){
            is MapScreenUIState.DataLoaded -> {
                Log.d("delete place", "loaded")
                mapData.clear()
                mapData.addAll(it.data)
            }

            MapScreenUIState.Loading -> {
                Log.d("delete place", "hello")
                viewModel.loadPlaces()
            }

            MapScreenUIState.UserNotAuthorized -> {
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
                    navigationRouter.navigateToAddEditPlaceScreen(null)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        navigation = navigationRouter
    ) {
        MapScreenContent(
            paddingValues = it,
            navigation = navigationRouter,
            data = mapData,
            actions = viewModel
        )
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreenContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    data: List<Place>,
    actions: MapActions
) {
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                mapToolbarEnabled = false
            )
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(49.194830191037845, 16.600317076466016), 9f)
    }

    val context = LocalContext.current

    var clusterRender by remember { mutableStateOf<CustomMapRenderer?>(null) }
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var clusterManager by remember { mutableStateOf<ClusterManager<Place>?>(null) }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    if (data.isNotEmpty()) {
        clusterManager?.addItems(data)
        clusterManager?.cluster()
    }

    fun deleteFromCluster(place: Place) {
        clusterManager?.removeItem(place)
        clusterManager?.cluster()
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxHeight(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ) {
            //if (data.isNotEmpty()) {
                MapEffect { map ->
                    if (googleMap == null) {
                        googleMap = map
                    }

                    if (clusterManager == null) {
                        clusterManager = ClusterManager(context, googleMap)

                        clusterManager?.apply {
                            algorithm = GridBasedAlgorithm()
                            renderer = CustomMapRenderer(context, googleMap!!, this)
                            addItems(data)
                            setOnClusterItemClickListener { place ->
                                selectedPlace = place
                                true
                            }
                        }
                    }

                    googleMap?.setOnCameraIdleListener {
                        clusterManager?.cluster()
                    }

                    if (data.isNotEmpty() && clusterManager != null) {
                        clusterManager?.addItems(data)
                        clusterManager?.cluster()  // Re-cluster the items
                    }
               // }
            }
        }

        selectedPlace?.let { place ->
            PlaceDetail(
                place = place,
                onEdit = {
                    navigation.navigateToAddEditPlaceScreen(place.id)
                },
                onDelete = {
                    actions.deletePlace(place)
                    deleteFromCluster(place)
                    selectedPlace = null
                },
                onClose = {
                    selectedPlace = null
                },
                context = LocalContext.current,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
}
