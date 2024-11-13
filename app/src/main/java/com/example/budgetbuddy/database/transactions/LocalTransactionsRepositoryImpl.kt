package com.example.budgetbuddy.database.transactions

import com.example.budgetbuddy.model.db.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTransactionsRepositoryImpl @Inject constructor(
    private val dao: TransactionsDao
) : ILocalTransactionsRepository {

    override fun getAllByUser(userId: String): Flow<List<Transaction>> {
        return dao.getAllByUser(userId)
    }

    override suspend fun insert(transaction: Transaction): Long {
        return dao.insert(transaction)
    }

    override suspend fun delete(transaction: Transaction) {
        dao.delete(transaction)
    }

    override fun getTransaction(id: Long, userId: String): Flow<Transaction> {
        return dao.getTransaction(id, userId)
    }

    override suspend fun update(transaction: Transaction) {
        return dao.update(transaction)
    }

}