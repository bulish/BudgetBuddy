package com.example.budgetbuddy.model.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

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
    var type: TransactionType,
    var receipt: String? = null,
    var category: TransactionCategory,
    var price: Double,
    var currency: String,
    var note: String?,
    var userId: String,
    var date: Date,
    var placeId: Long? = null
){

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

}
