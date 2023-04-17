package com.ua.weatherwise.data.remote.repositories

import com.ua.weatherwise.data.remote.api.WeatherApi
import com.ua.weatherwise.data.remote.models.entities.Weather
import retrofit2.Response
import retrofit2.http.QueryMap
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun getByCoordinates(@QueryMap queries: Map<String, String>): Response<Weather> {
        return weatherApi.getByCoordinates(queries)
    }

    suspend fun searchByCity(@QueryMap queries: Map<String, String>): Response<Weather> {
        return weatherApi.searchByCity(queries)
    }
}