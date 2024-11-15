package com.example.budgetbuddy.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "places")
data class Place(
    var name: String,
    var address: String,
    var category: PlaceCategory,
    var latitude: Double?,
    var longitude: Double?,
    var userId: String,
    var created: LocalDateTime = LocalDateTime.now(),
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    val updatedDateFormatted: String
        get() {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            return created.format(formatter)
        }
}
