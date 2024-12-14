package com.example.budgetbuddy.services.datastore

import com.example.budgetbuddy.model.PrimaryColor
import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    suspend fun setFirstRun()

    suspend fun getFirstRun(): Flow<Boolean>

    suspend fun setDarkTheme(isDark: Boolean)

    suspend fun getIsDarkTheme(): Flow<Boolean>

    suspend fun setPrimaryColor(color: PrimaryColor)

    suspend fun getPrimaryColor(): Flow<String>

    suspend fun setCurrency(currency: String)

    suspend fun getCurrency(): Flow<String>

    suspend fun setCurrencies(currencies: Map<String, Double>)

    suspend fun getCurrencies(): Flow<Map<String, Double>?>
}
