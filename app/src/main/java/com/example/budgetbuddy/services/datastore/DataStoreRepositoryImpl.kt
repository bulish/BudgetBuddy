package com.example.budgetbuddy.services.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.budgetbuddy.model.NotificationData
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject
import com.squareup.moshi.Moshi
import kotlinx.coroutines.delay
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
    private val jsonAdapter: JsonAdapter<NotificationData> =
        moshi.adapter(NotificationData::class.java)

    companion object {
        const val DATA = "Data"
        private const val IS_DARK_THEME = "IsDarkTheme"
        private const val NOTIFICATION_DATA = "notificationDAta"
        private const val FIRST_RUN = "firstRun"

        val isDarkTheme = booleanPreferencesKey(IS_DARK_THEME)
        val notificationData = stringPreferencesKey(NOTIFICATION_DATA)
        val firstRun = booleanPreferencesKey(FIRST_RUN)
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

    override suspend fun saveNotificationData(data: NotificationData) {
        context.dataStore.edit { preferences ->
            preferences[notificationData] = jsonAdapter.toJson(data)
        }

        delay(2000)

        val emptyData = NotificationData(false, 0, false)
        context.dataStore.edit { preferences ->
            preferences[notificationData] = jsonAdapter.toJson(emptyData)
        }
    }

    override suspend fun getNotificationData(): Flow<NotificationData?> {
        return context.dataStore.data
            .map { preferences ->
                if (!preferences.contains(notificationData)) {
                    val serializedData = preferences[notificationData]
                    jsonAdapter.fromJson(serializedData)
                } else {
                    null
                }
            }
            .catch { e ->
                e.printStackTrace()
                emit(null)
            }
    }
}
