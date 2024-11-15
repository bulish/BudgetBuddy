package com.example.budgetbuddy.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    var latitude: Double?,
    var longitude: Double?
)
