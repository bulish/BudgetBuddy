package com.example.budgetbuddy.database.transactions

import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class LocalTransactionsRepositoryImpl @Inject constructor(
    private val dao: TransactionsDao
) : ILocalTransactionsRepository {

    override fun getAllByUser(
        userId: String,
        category: TransactionCategory?
    ): Flow<List<Transaction>> {
        if (category != null) {
            return dao.getAllByUserAndCategory(userId, category.value)
        } else {
            return dao.getAllByUser(userId)
        }
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