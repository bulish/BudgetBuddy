package com.example.budgetbuddy.ui.screens.places.map

import com.example.budgetbuddy.ui.screens.places.addEditPlace.AddEditPlaceScreenData

sealed class MapScreenUIState {
    object UserNotAuthorized : MapScreenUIState()
}
