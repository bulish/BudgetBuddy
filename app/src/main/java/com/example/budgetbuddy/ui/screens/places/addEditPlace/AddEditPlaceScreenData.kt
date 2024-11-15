package com.example.budgetbuddy.ui.screens.places.addEditPlace

import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.PlaceCategory
import java.time.LocalDateTime

class AddEditPlaceScreenData {
    var place: Place = Place("", "", PlaceCategory.FOOD, null, null, "", LocalDateTime.now())
    var placeNameError: Int? = null
    var placeAddressError: Int? = null
    var placeCategoryError: Int? = null
    var placeLatitudeError: Int? = null
    var placeLongitudeError: Int? = null
}
