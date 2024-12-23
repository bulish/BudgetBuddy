package com.example.budgetbuddy.di

import com.example.budgetbuddy.database.BudgetBuddyDatabase
import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.TransactionsDao
import com.example.budgetbuddy.fake.FakeBudgetBuddyDatabase
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.PlaceCategory
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.model.db.TransactionType
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import javax.inject.Singleton
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DaoModule::class]
)
object FakeDaoModule {

    @Provides
    @Singleton
    fun provideFakeTransactionsDao(): TransactionsDao {
        return object : TransactionsDao {
            override fun getAllByUser(userId: String): Flow<List<Transaction>> {
                return flowOf(
                    listOf(
                        Transaction(
                            type = TransactionType.INCOME.value,
                            category = TransactionCategory.SALARY.value,
                            price = 20.5,
                            currency = "EUR",
                            userId = "",
                            note = ""
                        )
                    )
                )
            }

            override fun getAllByUserAndCategory(
                userId: String,
                category: String
            ): Flow<List<Transaction>> {
                return flowOf(
                    listOf(
                        Transaction(
                            type = TransactionType.INCOME.value,
                            category = TransactionCategory.SALARY.value,
                            price = 20.5,
                            currency = "EUR",
                            userId = "",
                            note = ""
                        )
                    )
                )
            }

            override suspend fun insert(transaction: Transaction): Long {
                // Můžeš vrátit nějakou hodnotu, např. ID nového záznamu
                return 1
            }

            override suspend fun delete(transaction: Transaction) {
                // Simulace smazání
            }

            override fun getTransaction(id: Long, userId: String): Flow<Transaction> {
                return flowOf(
                    Transaction(
                        type = TransactionType.INCOME.value,
                        category = TransactionCategory.SALARY.value,
                        price = 20.5,
                        currency = "EUR",
                        userId = "",
                        note = ""
                    )
                )
            }

            override suspend fun update(transaction: Transaction) {
                // Simulace aktualizace
            }
        }
    }

    @Provides
    @Singleton
    fun provideFakePlacesDao(): PlacesDao {
        return object : PlacesDao {
            override fun getAllByUser(userId: String): Flow<List<Place>> {
                return flowOf(
                    listOf(
                        Place(
                            name = "Kavarna",
                            address = "Brno",
                            category = PlaceCategory.FOOD,
                            latitude = 53.4,
                            longitude = 15.3,
                            userId = "",
                            imageName = ""
                        )
                    )
                )
            }

            override suspend fun insert(place: Place): Long {
                return 1 // ID nového místa
            }

            override suspend fun delete(place: Place) {
                // Simulace smazání
            }

            override fun getPlace(id: Long, userId: String): Flow<Place> {
                return flowOf(
                    Place(
                        name = "Kavarna",
                        address = "Brno",
                        category = PlaceCategory.FOOD,
                        latitude = 53.4,
                        longitude = 15.3,
                        userId = "",
                        imageName = ""
                    )
                )
            }

            override suspend fun update(place: Place) {
                // Simulace aktualizace
            }
        }
    }
}
