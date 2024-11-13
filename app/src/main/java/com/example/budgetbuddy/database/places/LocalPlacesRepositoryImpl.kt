package com.example.budgetbuddy.database.places

import com.example.budgetbuddy.model.db.Place
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalPlacesRepositoryImpl @Inject constructor(
    private val dao: PlacesDao
) : ILocalPlacesRepository {

    override fun getAllByUser(userId: String): Flow<List<Place>> {
        return dao.getAllByUser(userId)
    }

    override suspend fun insert(place: Place): Long {
        return dao.insert(place)
    }

    override suspend fun delete(place: Place) {
        dao.delete(place)
    }

    override fun getPlace(id: Long, userId: String): Flow<Place> {
        return dao.getPlace(id, userId)
    }

    override suspend fun update(place: Place) {
        return dao.update(place)
    }

}
