package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.model.db.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePlacesDao : PlacesDao {

    private val places = mutableListOf<Place>()

    override fun getAllByUser(userId: String): Flow<List<Place>> {
        return flow {
            emit(places.filter { it.userId == userId })
        }
    }

    override suspend fun insert(place: Place): Long {
        places.add(place)
        return place.id ?: 0L
    }

    override suspend fun delete(place: Place) {
        places.remove(place)
    }

    override fun getPlace(id: Long, userId: String): Flow<Place> {
        return flow {
            val place = places.find { it.id == id && it.userId == userId }
            place?.let { emit(it) }
        }
    }

    override suspend fun update(place: Place) {
        val index = places.indexOfFirst { it.id == place.id && it.userId == place.userId }
        if (index != -1) {
            places[index] = place
        }
    }
}
