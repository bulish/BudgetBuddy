package com.example.budgetbuddy.ui.screens.places.addEditPlace

sealed class AddEditPlaceUIState {
    object Loading : AddEditPlaceUIState()
    class PlaceSaved(
        val message: Int
    ) : AddEditPlaceUIState()
    object UserNotAuthorized : AddEditPlaceUIState()
    class PlaceChanged(val data: AddEditPlaceScreenData) : AddEditPlaceUIState()
    object PlaceDeleted : AddEditPlaceUIState()
}
