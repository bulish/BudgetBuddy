package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.transactions.TransactionsDao
import com.example.budgetbuddy.model.db.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTransactionsDao : TransactionsDao {

    private val transactions = mutableListOf<Transaction>()

    override fun getAllByUser(userId: String): Flow<List<Transaction>> {
        return flow {
            emit(transactions.filter { it.userId == userId })
        }
    }

    override fun getAllByUserAndCategory(userId: String, category: String): Flow<List<Transaction>> {
        return flow {
            emit(transactions.filter { it.userId == userId && it.category == category })
        }
    }

    override suspend fun insert(transaction: Transaction): Long {
        transactions.add(transaction)
        return transaction.id ?: 0L
    }

    override suspend fun delete(transaction: Transaction) {
        transactions.remove(transaction)
    }

    override fun getTransaction(id: Long, userId: String): Flow<Transaction> {
        return flow {
            val transaction = transactions.find { it.id == id && it.userId == userId }
            transaction?.let { emit(it) }
        }
    }

    override suspend fun update(transaction: Transaction) {
        val index = transactions.indexOfFirst { it.id == transaction.id && it.userId == transaction.userId }
        if (index != -1) {
            transactions[index] = transaction
        }
    }
}
