package com.ua.weatherwise.domain.usecases

import com.ua.weatherwise.utils.Constants.Companion.API_KEY
import com.ua.weatherwise.utils.Constants.Companion.QUERY_API_KEY
import com.ua.weatherwise.utils.Constants.Companion.QUERY_CITY
import com.ua.weatherwise.utils.Constants.Companion.QUERY_LATITUDE
import com.ua.weatherwise.utils.Constants.Companion.QUERY_LONGITUDE
import com.ua.weatherwise.utils.Constants.Companion.QUERY_UNITS

class ApplyQueryUseCase {
    operator fun invoke(coordinates: DoubleArray, units: String): Map<String, String> {
        return mapOf(
            QUERY_LATITUDE to coordinates[0].toString(),
            QUERY_LONGITUDE to coordinates[1].toString(),
            QUERY_API_KEY to API_KEY,
            QUERY_UNITS to units
        )
    }
    
    operator fun invoke(city: String, units: String): Map<String, String> {
        return mapOf(
            QUERY_CITY to city,
            QUERY_API_KEY to API_KEY,
            QUERY_UNITS to units
        )
    }
}