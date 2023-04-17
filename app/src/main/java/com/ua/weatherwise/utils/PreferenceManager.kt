package com.ua.weatherwise.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.ua.weatherwise.utils.Constants.Companion.DATA_STORE
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_COORDINATES
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_UNITS
import com.ua.weatherwise.utils.Constants.Companion.PREFERENCE_COORDINATES
import com.ua.weatherwise.utils.Constants.Companion.PREFERENCE_UNITS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Singleton

@Singleton
class PreferenceManager(
    @ApplicationContext context: Context
) {
    private val dataStore = context.createDataStore(name = DATA_STORE)

    companion object {
        private val COORDINATES = preferencesKey<DoubleArray>(PREFERENCE_COORDINATES)
        private val UNITS = preferencesKey<String>(PREFERENCE_UNITS)
    }

    suspend fun setCoordinates(coordinates: DoubleArray) =
        dataStore.edit { preferences -> preferences[COORDINATES] = coordinates }

    val getCoordinates: Flow<DoubleArray> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences -> preferences[COORDINATES] ?: DEFAULT_COORDINATES }

    suspend fun setUnits(units: String) =
        dataStore.edit { preferences -> preferences[UNITS] = units }

    val getUnits: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences -> preferences[UNITS] ?: DEFAULT_UNITS }
}