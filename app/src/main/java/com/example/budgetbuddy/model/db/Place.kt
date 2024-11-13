package com.example.budgetbuddy.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Place(
    var name: String,
    var address: String?,
    var category: PlaceCategory,
    var latitude: Double,
    var longitude: Double,
    var userId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
