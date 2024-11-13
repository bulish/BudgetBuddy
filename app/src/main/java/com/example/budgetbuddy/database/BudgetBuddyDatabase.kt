package com.example.budgetbuddy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.TransactionsDao
import com.example.budgetbuddy.model.db.Place

@Database(entities = [Place::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class BudgetBuddyDatabase : RoomDatabase() {

    abstract fun placesDao(): PlacesDao
    abstract fun transactionsDao(): TransactionsDao

    companion object {
        private var INSTANCE: BudgetBuddyDatabase? = null

        fun getDatabase(context: Context): BudgetBuddyDatabase {
            if (INSTANCE == null) {
                synchronized(BudgetBuddyDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            BudgetBuddyDatabase::class.java, "budget_buddy_database"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}
