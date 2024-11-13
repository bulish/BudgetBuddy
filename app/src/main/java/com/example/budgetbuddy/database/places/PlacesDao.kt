package com.example.budgetbuddy.database.places

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgetbuddy.model.db.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {

    @Query("SELECT * FROM places WHERE userId=:userId")
    fun getAllByUser(userId: String): Flow<List<Place>>

    @Insert
    suspend fun insert(place: Place): Long

    @Delete
    suspend fun delete(place: Place)

    @Query("SELECT * FROM places WHERE id = :id AND userId = :userId")
    fun getPlace(id: Long, userId: String): Flow<Place>

    @Update
    suspend fun update(place: Place)

}
