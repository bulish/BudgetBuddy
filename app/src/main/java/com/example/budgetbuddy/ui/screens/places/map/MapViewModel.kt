package com.example.budgetbuddy.ui.screens.places.map

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel(), MapActions {

    var latitude: Double? = null
    var longitude: Double? = null
    var locationChanged = false
    override fun onLocationChanged(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        locationChanged = true
    }
}





