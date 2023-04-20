package com.ua.weatherwise.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.ua.weatherwise.utils.Constants.Companion.DATA_STORE_NAME
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_LATITUDE
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_LONGITUDE
import com.ua.weatherwise.utils.Constants.Companion.PREFERENCE_LATITUDE
import com.ua.weatherwise.utils.Constants.Companion.PREFERENCE_LONGITUDE
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

@ViewModelScoped
class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    private object PreferenceKeys {
        val latitude = doublePreferencesKey(PREFERENCE_LATITUDE)
        val longitude = doublePreferencesKey(PREFERENCE_LONGITUDE)
    }

    suspend fun setCoordinates(latitude: Double, longitude: Double) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.latitude] = latitude
            preferences[PreferenceKeys.longitude] = longitude
        }
    }

    val getCoordinates: Flow<Coordinates> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val latitude = preferences[PreferenceKeys.latitude] ?: DEFAULT_LATITUDE
            val longitude = preferences[PreferenceKeys.longitude] ?: DEFAULT_LONGITUDE
            Coordinates(latitude, longitude)

        }
}

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)