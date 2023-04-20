package com.ua.weatherwise.presentation.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ua.weatherwise.data.remote.models.entities.Weather
import com.ua.weatherwise.data.repositories.WeatherRepository
import com.ua.weatherwise.utils.Constants.Companion.API_KEY
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_UNITS
import com.ua.weatherwise.utils.Constants.Companion.QUERY_API_KEY
import com.ua.weatherwise.utils.Constants.Companion.QUERY_LATITUDE
import com.ua.weatherwise.utils.Constants.Companion.QUERY_LONGITUDE
import com.ua.weatherwise.utils.Constants.Companion.QUERY_UNITS
import com.ua.weatherwise.utils.NetworkHelper
import com.ua.weatherwise.utils.NetworkResult
import com.ua.weatherwise.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val networkHelper: NetworkHelper,
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    val getCoordinates = preferenceManager.getCoordinates.asLiveData(Dispatchers.IO)

    fun setCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceManager.setCoordinates(latitude, longitude)
        }
    }

    private val _weather: MutableLiveData<NetworkResult<Weather>> = MutableLiveData()
    val weather: LiveData<NetworkResult<Weather>>
        get() = _weather

    fun fetchWeatherByCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _weather.value = NetworkResult.Loading()
            if (networkHelper.hasInternetConnection()) {
                try {
                    _weather.value = NetworkResult.toNetworkResult(
                        weatherRepository.remote.getByCoordinates(applyQueries(latitude, longitude))
                    )
                } catch (e: Exception) {
                    _weather.value = NetworkResult.Error("City not found")
                }
            } else {
                _weather.value = NetworkResult.Error("No Internet Connection")
            }
        }
    }

    private fun applyQueries(latitude: Double, longitude: Double): Map<String, String> {
        return mapOf(
            QUERY_LATITUDE to latitude.toString(),
            QUERY_LONGITUDE to longitude.toString(),
            QUERY_API_KEY to API_KEY,
            QUERY_UNITS to DEFAULT_UNITS
        )
    }
}