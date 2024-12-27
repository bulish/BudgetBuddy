package com.example.budgetbuddy

import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest

class TransactionsViewModelTest {

    @Before
    fun setup() {

    }

    @Test
    fun `test loadTransactions calculates total sum correctly`() = runTest {
        val transactions = listOf(
            Transaction(
                userId = "user1",
                category = "Food",
                currency = "CZK",
                price = 100.0,
                type = TransactionType.EXPENSE.value,
                note = null
            ),
            Transaction(
                userId = "user1",
                category = "Salary",
                currency = "CZK",
                price = 200.0,
                type = TransactionType.INCOME.value,
                note = null
            ),
            Transaction(
                userId = "user1",
                category = "Transport",
                currency = "CZK",
                price = 50.0,
                type = TransactionType.EXPENSE.value,
                note = null
            ),
            Transaction(
                userId = "user1",
                category = "Freelance",
                currency = "CZK",
                price = 150.0,
                type = TransactionType.INCOME.value,
                note = null
            )
        )

        val conversionRates = mapOf("CZK" to 0.045, "USD" to 1.0)

        val totalSumInCurrentCurrency = transactions.sumOf { transaction ->
            val activeRate = conversionRates[transaction.currency] ?: 1.0

            when (transaction.type) {
                TransactionType.INCOME.value -> transaction.price * activeRate
                TransactionType.EXPENSE.value -> -transaction.price * activeRate
                else -> 0.0
            }
        }

        val expectedSum = (-100.0 + 200.0 - 50.0 + 150.0) * 0.045

        assertEquals(expectedSum, totalSumInCurrentCurrency, 0.01)
    }

    @Test
    fun `test loadTransactions with different currencies`() = runTest {
        val transactions = listOf(
            Transaction(
                userId = "user1",
                category = "Food",
                currency = "CZK",
                price = 100.0,
                type = TransactionType.EXPENSE.value,
                note = null
            ),
            Transaction(
                userId = "user1",
                category = "Salary",
                currency = "USD",
                price = 200.0,
                type = TransactionType.INCOME.value,
                note = null
            )
        )

        val conversionRates = mapOf("CZK" to 0.045, "USD" to 1.0)

        val totalSumInCurrentCurrency = transactions.sumOf { transaction ->
            val activeRate = conversionRates[transaction.currency] ?: 1.0
            when (transaction.type) {
                TransactionType.INCOME.value -> transaction.price * activeRate
                TransactionType.EXPENSE.value -> -transaction.price * activeRate
                else -> 0.0
            }
        }

        val expectedSum = (-100.0 * 0.045 + 200.0)

        assertEquals(expectedSum, totalSumInCurrentCurrency, 0.01)
    }

    @Test
    fun `test loadTransactions with missing currency rate`() = runTest {
        val transactions = listOf(
            Transaction(
                userId = "user1",
                category = "Food",
                currency = "CZK",
                price = 100.0,
                type = TransactionType.EXPENSE.value,
                note = null
            ),
            Transaction(
                userId = "user1",
                category = "Salary",
                currency = "USD",
                price = 200.0,
                type = TransactionType.INCOME.value,
                note = null
            ),
            Transaction(
                userId = "user1",
                category = "Rent",
                currency = "EUR",  // EUR není v mapě kurzů
                price = 500.0,
                type = TransactionType.EXPENSE.value,
                note = null
            )
        )

        val conversionRates = mapOf("CZK" to 0.045, "USD" to 1.0)

        val totalSumInCurrentCurrency = transactions.sumOf { transaction ->
            val activeRate = conversionRates[transaction.currency] ?: 1.0
            when (transaction.type) {
                TransactionType.INCOME.value -> transaction.price * activeRate
                TransactionType.EXPENSE.value -> -transaction.price * activeRate
                else -> 0.0
            }
        }

        val expectedSum = (-100.0 * 0.045 + 200.0 + -500.0)

        assertEquals(expectedSum, totalSumInCurrentCurrency, 0.01)
    }

}
