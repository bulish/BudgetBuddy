package com.example.budgetbuddy.model.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = Place::class,
        parentColumns = ["id"],
        childColumns = ["placeId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Transaction(
    var type: String,
    var receipt: String? = null,
    var category: String,
    var price: Double,
    var currency: String,
    var note: String?,
    var userId: String,
    var date: LocalDateTime = LocalDateTime.now(),
    var placeId: Long? = null
){

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    val updatedDateFormatted: String
        get() {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            return date.format(formatter)
        }

}
