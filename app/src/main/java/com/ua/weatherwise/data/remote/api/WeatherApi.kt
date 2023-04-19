package com.ua.weatherwise.data.remote.api

import com.ua.weatherwise.data.remote.models.entities.Weather
import com.ua.weatherwise.utils.Constants.Companion.ENDPOINT_WEATHER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherApi {
    @GET(ENDPOINT_WEATHER)
    suspend fun getByCoordinates(
        @QueryMap queries: Map<String, String>
    ): Response<Weather>
}