package com.example.budgetbuddy.ui.screens.places.addEditPlace

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.database.places.ILocalPlacesRepository
import com.example.budgetbuddy.model.db.PlaceCategory
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddEditPlaceViewModel @Inject constructor(
    private val repository: ILocalPlacesRepository,
    private val authService: IAuthService,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), AddEditPlaceScreenActions {

    private val _addEditPlaceUIState: MutableStateFlow<AddEditPlaceUIState> =
        MutableStateFlow(AddEditPlaceUIState.Loading)

    val addEditPlaceUIState = _addEditPlaceUIState.asStateFlow()

    private var data = AddEditPlaceScreenData()

    init {
        if (authService.getCurrentUser() == null) {
            _addEditPlaceUIState.update {
                AddEditPlaceUIState.UserNotAuthorized
            }
        }
    }

    override fun savePlace(){
        data.place.name = data.place.name.trim()
        data.place.address = data.place.address.trim()

        val name = data.place.name
        val address = data.place.address
        val category = data.place.category
        val latitude = data.place.latitude
        val longitude = data.place.longitude

        data.place.created = LocalDateTime.now()

        authService.getUserID()?.let { userId ->
            data.place.userId = userId
        }

        if (name.isNotEmpty() && address.isNotEmpty() && category != null
            && latitude != null && longitude != null) {
            viewModelScope.launch {
                if (data.place.id != null){
                    repository.update(data.place)
                } else {
                    repository.insert(data.place)
                }
            }

            _addEditPlaceUIState.update {
                AddEditPlaceUIState.PlaceSaved(R.string.add_edit_place_success)
            }
        }
        else {
            _addEditPlaceUIState.update {
                AddEditPlaceUIState.PlaceChanged(data)
            }

            if (name.isEmpty()) {
                data.placeNameError = R.string.cannot_be_empty
            }

            if (address.isEmpty()) {
                data.placeAddressError = R.string.cannot_be_empty
            }

            if (category == null) {
                data.placeCategoryError = R.string.cannot_be_empty
            }

            if (latitude == null) {
                data.placeLatitudeError = R.string.cannot_be_empty
            }

            if (longitude == null) {
                data.placeLongitudeError = R.string.cannot_be_empty
            }
        }
    }

    override fun onPlaceAddressChanged(address: String) {
        data.place.address = address
        _addEditPlaceUIState.update {
            AddEditPlaceUIState.PlaceChanged(data)
        }
    }

    override fun onPlaceCategoryChanged(category: PlaceCategory) {
        data.place.category = category
        _addEditPlaceUIState.update {
            AddEditPlaceUIState.PlaceChanged(data)
        }
    }

    override fun deletePlace() {
        viewModelScope.launch {
            repository.delete(data.place)
            _addEditPlaceUIState.update {
                AddEditPlaceUIState.PlaceChanged(data)
            }
        }
    }

    override fun onLocationChanged(latitude: Double?, longitude: Double?) {
        data.place.latitude = latitude
        data.place.longitude = longitude
        _addEditPlaceUIState.update {
            AddEditPlaceUIState.PlaceChanged(data)
        }
    }

    override fun onPlaceImageChanged(icon: String) {
        data.place.imageName = icon
        _addEditPlaceUIState.update {
            AddEditPlaceUIState.PlaceChanged(data)
        }
    }

    override fun onPlaceNameChanged(name: String) {
        data.place.name = name
        _addEditPlaceUIState.update {
            AddEditPlaceUIState.PlaceChanged(data)
        }
    }

    override fun loadPlace(id: Long?) {
        authService.getUserID()?.let { userId ->
            if (id != null){
                viewModelScope.launch {
                    repository.getPlace(id, userId).collect { place ->
                        if (place != null) {
                            data.place = place
                            _addEditPlaceUIState.update {
                                AddEditPlaceUIState.PlaceChanged(data)
                            }
                        }
                    }
                }
            }
        }
    }
}
