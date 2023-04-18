package com.ua.weatherwise.data.remote.repositories

import com.ua.weatherwise.data.remote.api.WeatherApi
import com.ua.weatherwise.data.remote.models.entities.Weather
import retrofit2.Response
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun getByCoordinates(queries: Map<String, String>): Response<Weather> {
        return weatherApi.getByCoordinates(queries)
    }

    suspend fun searchByCity(queries: Map<String, String>): Response<Weather> {
        return weatherApi.searchByCity(queries)
    }
}