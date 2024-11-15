package com.example.budgetbuddy.database.transactions

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.budgetbuddy.model.db.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions WHERE userId=:userId")
    fun getAllByUser(userId: String): Flow<List<Transaction>>

    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE id = :id AND userId = :userId")
    fun getTransaction(id: Long, userId: String): Flow<Transaction>

    @Update
    suspend fun update(transaction: Transaction)

}
