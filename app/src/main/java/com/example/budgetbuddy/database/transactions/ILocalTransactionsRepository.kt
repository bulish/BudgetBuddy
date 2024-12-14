package com.example.budgetbuddy.database.transactions

import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import kotlinx.coroutines.flow.Flow

interface ILocalTransactionsRepository {
    fun getAllByUser(userId: String, category: TransactionCategory?): Flow<List<Transaction>>
    suspend fun insert(transaction: Transaction): Long
    suspend fun delete(transaction: Transaction)
    fun getTransaction(id: Long, userId: String): Flow<Transaction>
    suspend fun update(transaction: Transaction)
}
