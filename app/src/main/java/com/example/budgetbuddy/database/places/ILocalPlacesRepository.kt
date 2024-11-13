package com.example.budgetbuddy.database.places

import com.example.budgetbuddy.model.db.Place
import kotlinx.coroutines.flow.Flow

interface ILocalPlacesRepository {
    fun getAllByUser(userId: String): Flow<List<Place>>
    suspend fun insert(place: Place): Long
    suspend fun delete(place: Place)
    fun getPlace(id: Long, userId: String): Flow<Place>
    suspend fun update(place: Place)
}
