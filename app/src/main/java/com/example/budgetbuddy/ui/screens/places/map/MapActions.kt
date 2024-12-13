package com.example.budgetbuddy.ui.screens.places.map

import com.example.budgetbuddy.model.db.Place

interface MapActions {

    fun deletePlace(place: Place)
    fun loadPlaces()

}
