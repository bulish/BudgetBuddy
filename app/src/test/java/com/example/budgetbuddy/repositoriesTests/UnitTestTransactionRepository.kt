package com.example.budgetbuddy.repositoriesTests

import com.example.budgetbuddy.di.FakeTransactionsDao
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.model.db.TransactionType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FakeTransactionsDaoTest {

    private lateinit var transactionsDao: FakeTransactionsDao

    @Before
    fun setup() {
        transactionsDao = FakeTransactionsDao()
    }

    @Test
    fun testInsertTransaction() = runBlocking {
        val transaction = Transaction(
            userId = "user1",
            category = "Food",
            currency = "CZK",
            price = 100.0,
            type = TransactionType.EXPENSE.value,
            note = null
        )

        val insertedId = transactionsDao.insert(transaction)

        assertEquals(0L, insertedId)

        val fetchedTransaction = transactionsDao.getAllByUser( "user1").first()
        assertNotNull(fetchedTransaction)
        assertEquals(transaction.id, fetchedTransaction[0].id)
    }

    @Test
    fun testUpdateTransaction() = runBlocking {
        val transaction = Transaction(
            userId = "user1",
            category = "Food",
            currency = "CZK",
            price = 100.0,
            type = TransactionType.EXPENSE.value,
            note = null
        )

        transactionsDao.insert(transaction)

        val updatedTransaction = transaction.copy(price = 150.0)
        transactionsDao.update(updatedTransaction)

        val fetchedTransaction = transactionsDao.getAllByUser("user1").first()
        assertEquals(150.0, fetchedTransaction[0].price, 0.01)
    }

    @Test
    fun testDeleteTransaction() = runBlocking {
        val transaction = Transaction(
            userId = "user1",
            category = "Food",
            currency = "CZK",
            price = 100.0,
            type = TransactionType.EXPENSE.value,
            note = null
        )

        transactionsDao.insert(transaction)
        transactionsDao.delete(transaction)

        val fetchedTransaction = transactionsDao.getAllByUser("user1").firstOrNull()
        assertEquals(fetchedTransaction?.size, 0)
    }

    @Test
    fun testGetAllTransactionsByUser() = runBlocking {
        val transaction1 = Transaction(
            userId = "user1",
            category = "Food",
            currency = "CZK",
            price = 100.0,
            type = TransactionType.EXPENSE.value,
            note = null
        )

        val transaction2 = Transaction(
            userId = "user1",
            category = "Food",
            currency = "CZK",
            price = 100.0,
            type = TransactionType.EXPENSE.value,
            note = null
        )

        transactionsDao.insert(transaction1)
        transactionsDao.insert(transaction2)

        val transactions = transactionsDao.getAllByUser("user1").first()
        assertEquals(2, transactions.size)
        assertTrue(transactions.contains(transaction1))
        assertTrue(transactions.contains(transaction2))
    }

    @Test
    fun testGetAllTransactionsByUserAndCategory() = runBlocking {
        val transaction1 = Transaction(
            userId = "user1",
            category = TransactionCategory.SALARY.value,
            currency = "CZK",
            price = 100.0,
            type = TransactionType.EXPENSE.value,
            note = null
        )

        val transaction2 = Transaction(
            userId = "user1",
            category = TransactionCategory.SALARY.value,
            currency = "CZK",
            price = 100.0,
            type = TransactionType.EXPENSE.value,
            note = null
        )

        transactionsDao.insert(transaction1)
        transactionsDao.insert(transaction2)

        val foodTransactions = transactionsDao.getAllByUserAndCategory("user1", TransactionCategory.SALARY.value).first()
        assertEquals(2, foodTransactions.size)
        assertTrue(foodTransactions.contains(transaction1))
    }
}
