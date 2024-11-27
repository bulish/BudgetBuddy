package com.example.budgetbuddy.ui.screens.places.map

import com.example.budgetbuddy.model.db.Place

sealed class MapScreenUIState {

    object UserNotAuthorized : MapScreenUIState()
    class DataLoaded(val data: List<Place>) : MapScreenUIState()
    object Loading: MapScreenUIState()

}
