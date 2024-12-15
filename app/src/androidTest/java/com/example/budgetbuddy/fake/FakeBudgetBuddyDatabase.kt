package com.example.budgetbuddy.fake

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.example.budgetbuddy.database.BudgetBuddyDatabase
import com.example.budgetbuddy.database.Converters
import com.example.budgetbuddy.database.places.PlacesDao
import com.example.budgetbuddy.database.transactions.TransactionsDao
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.model.db.Transaction

@Database(entities = [Place::class, Transaction::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FakeBudgetBuddyDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: BudgetBuddyDatabase? = null

        fun getInMemoryDatabase(context: Context): BudgetBuddyDatabase {
            if (INSTANCE == null) {
                synchronized(FakeBudgetBuddyDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.inMemoryDatabaseBuilder(
                            context.applicationContext,
                            BudgetBuddyDatabase::class.java
                        )
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE place ADD COLUMN imageName TEXT")
            }
        }
    }
}
