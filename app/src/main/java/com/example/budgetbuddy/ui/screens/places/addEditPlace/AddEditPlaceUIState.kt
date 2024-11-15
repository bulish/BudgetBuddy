package com.example.budgetbuddy.ui.screens.places.addEditPlace

sealed class AddEditPlaceUIState {
    object Loading : AddEditPlaceUIState()
    object PlaceSaved : AddEditPlaceUIState()
    class PlaceChanged(val data: AddEditPlaceScreenData) : AddEditPlaceUIState()
    object PlaceDeleted : AddEditPlaceUIState()
}
