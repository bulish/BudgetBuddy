package com.example.budgetbuddy.ui.screens.places.addEditPlace

import com.example.budgetbuddy.model.db.PlaceCategory

interface AddEditPlaceScreenActions {
    fun onPlaceNameChanged(name: String)
    fun onPlaceAddressChanged(address: String)
    fun onPlaceCategoryChanged(category: PlaceCategory)
    fun onLocationChanged(latitude: Double?, longitude: Double?)
    fun savePlace()
    fun loadPlace(id: Long?)
    fun deletePlace()
}
