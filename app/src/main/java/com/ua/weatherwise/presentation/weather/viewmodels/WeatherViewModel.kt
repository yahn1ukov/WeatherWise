package com.ua.weatherwise.presentation.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ua.weatherwise.data.remote.models.entities.Weather
import com.ua.weatherwise.data.repositories.WeatherRepository
import com.ua.weatherwise.utils.Constants.Companion.API_KEY
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_CITY
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_COORDINATES
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_UNITS
import com.ua.weatherwise.utils.Constants.Companion.QUERY_API_KEY
import com.ua.weatherwise.utils.Constants.Companion.QUERY_CITY
import com.ua.weatherwise.utils.Constants.Companion.QUERY_LATITUDE
import com.ua.weatherwise.utils.Constants.Companion.QUERY_LONGITUDE
import com.ua.weatherwise.utils.Constants.Companion.QUERY_UNITS
import com.ua.weatherwise.utils.NetworkHelper
import com.ua.weatherwise.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _weather: MutableLiveData<NetworkResult<Weather>> = MutableLiveData()
    val weather: LiveData<NetworkResult<Weather>>
        get() = _weather

    fun fetchWeatherByCoordinates() {
        viewModelScope.launch {
            _weather.value = NetworkResult.Loading()
            if (networkHelper.hasInternetConnection()) {
                try {
                    _weather.value = NetworkResult.toNetworkResult(
                        weatherRepository.remote.getByCoordinates(
                            applyQueries(DEFAULT_COORDINATES, DEFAULT_UNITS)
                        )
                    )
                } catch (e: Exception) {
                    _weather.value = NetworkResult.Error("City not found")
                }
            } else {
                _weather.value = NetworkResult.Error("No Internet Connection")
            }
        }
    }

    fun fetchWeatherByCity() {
        viewModelScope.launch {
            _weather.value = NetworkResult.Loading()
            if (networkHelper.hasInternetConnection()) {
                try {
                    _weather.value = NetworkResult.toNetworkResult(
                        weatherRepository.remote.searchByCity(
                            applyQueries(
                                DEFAULT_CITY,
                                DEFAULT_UNITS
                            )
                        )
                    )
                } catch (e: Exception) {
                    _weather.value = NetworkResult.Error("City not found")
                }
            } else {
                _weather.value = NetworkResult.Error("No Internet Connection")
            }
        }
    }

    private fun applyQueries(coordinates: DoubleArray, units: String): Map<String, String> {
        return mapOf(
            QUERY_LATITUDE to coordinates[0].toString(),
            QUERY_LONGITUDE to coordinates[1].toString(),
            QUERY_API_KEY to API_KEY,
            QUERY_UNITS to units
        )
    }

    private fun applyQueries(city: String, units: String): Map<String, String> {
        return mapOf(
            QUERY_CITY to city,
            QUERY_API_KEY to API_KEY,
            QUERY_UNITS to units
        )
    }
}