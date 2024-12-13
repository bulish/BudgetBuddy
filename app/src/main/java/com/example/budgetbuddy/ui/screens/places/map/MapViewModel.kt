package com.example.budgetbuddy.ui.screens.places.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import com.example.budgetbuddy.ui.screens.places.addEditPlace.AddEditPlaceUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: ILocalPlacesRepository,
    private val authService: AuthService,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), MapActions {

    private val _uiState: MutableStateFlow<MapScreenUIState> = MutableStateFlow(value = MapScreenUIState.Loading)
    val uiState: StateFlow<MapScreenUIState> get() = _uiState.asStateFlow()

    private val numberOfMarkers = 2

    init {
        if (authService.getCurrentUser() == null) {
            _uiState.update {
                MapScreenUIState.UserNotAuthorized
            }
        }
    }

    override fun loadPlaces() {
        val userID = authService.getUserID()
        if (userID != null) {
            viewModelScope.launch {
                repository.getAllByUser(userID).collect {data ->
                    _uiState.update {
                        MapScreenUIState.DataLoaded(data)
                    }
                }
            }
        }
    }

    override fun deletePlace(place: Place) {
        viewModelScope.launch {
            repository.delete(place)
            _uiState.update {
                MapScreenUIState.Loading
            }
        }
    }

}





