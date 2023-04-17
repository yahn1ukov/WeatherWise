package com.ua.weatherwise.domain.usecases.weather

import com.ua.weatherwise.data.remote.models.entities.Weather
import com.ua.weatherwise.data.repositories.WeatherRepository
import com.ua.weatherwise.domain.models.NetworkResult
import com.ua.weatherwise.domain.usecases.ApplyQueryUseCase
import javax.inject.Inject

class SearchByCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val applyQueryUseCase: ApplyQueryUseCase
) {
    suspend fun execute(city: String, units: String): NetworkResult<Weather> {
        return NetworkResult.toNetworkResult(
            weatherRepository.remote.searchByCity(
                applyQueryUseCase(city, units)
            )
        )
    }
}