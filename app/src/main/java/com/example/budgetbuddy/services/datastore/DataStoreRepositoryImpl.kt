package com.example.budgetbuddy.services.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.budgetbuddy.model.PrimaryColor
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class DataStoreRepositoryImpl @Inject constructor(private val context: Context)
    : IDataStoreRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val currencyType = Types.newParameterizedType(Map::class.java, String::class.java, java.lang.Double::class.java)

    private val jsonAdapterCurrencies: JsonAdapter<Map<String, Double>> =
        moshi.adapter(currencyType)

    companion object {
        const val DATA = "Data"
        private const val IS_DARK_THEME = "IsDarkTheme"
        private const val CURRENCY = "CZK"
        private const val FIRST_RUN = "firstRun"
        private const val PRIMARY_COLOR = "primaryColor"
        private const val CURRENCIES = "currencies"

        val isDarkTheme = booleanPreferencesKey(IS_DARK_THEME)
        val firstRun = booleanPreferencesKey(FIRST_RUN)
        var currencyData = stringPreferencesKey(CURRENCY)
        var primaryColor = stringPreferencesKey(PRIMARY_COLOR)
        var allCurrencies = stringPreferencesKey(CURRENCIES)
    }

    override suspend fun getIsDarkTheme(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[isDarkTheme] ?: true
            }
            .catch { e ->
                e.printStackTrace()
                emit(true)
            }
    }

    override suspend fun setCurrency(currency: String) {
        context.dataStore.edit { preferences ->
            preferences[currencyData] = currency
        }
    }

    override suspend fun getCurrency(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[currencyData] ?: "CZK"
            }
            .catch { e ->
                e.printStackTrace()
                emit("CZK")
            }
    }

    override suspend fun setFirstRun() {
        context.dataStore.edit { preferences ->
            preferences[firstRun] = false
        }
    }

    override suspend fun getFirstRun(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[firstRun] ?: true
            }
            .catch { e ->
                e.printStackTrace()
                emit(true)
            }
    }

    override suspend fun setDarkTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[isDarkTheme] = isDark
        }
    }

    override suspend fun setCurrencies(currencies: Map<String, Double>) {
        context.dataStore.edit { preferences ->
            preferences[allCurrencies] = jsonAdapterCurrencies.toJson(currencies)
        }
    }

    override suspend fun getCurrencies(): Flow<Map<String, Double>?> {
        return context.dataStore.data
            .map { preferences ->
                val serializedData = preferences[allCurrencies]
                val data = if (serializedData != null) jsonAdapterCurrencies.fromJson(serializedData) else null
                data
            }
            .catch { e ->
                e.printStackTrace()
                emit(null)
            }
    }

    override suspend fun setPrimaryColor(color: PrimaryColor) {
        context.dataStore.edit { preferences ->
            preferences[primaryColor] = color.name
        }
    }

    override suspend fun getPrimaryColor(): Flow<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[primaryColor] ?: PrimaryColor.GREEN.name
            }
            .catch { e ->
                e.printStackTrace()
                emit(PrimaryColor.GREEN.name)
            }
    }

}
