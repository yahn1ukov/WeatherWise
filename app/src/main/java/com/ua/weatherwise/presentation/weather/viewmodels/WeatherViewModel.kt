package com.ua.weatherwise.presentation.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ua.weatherwise.data.remote.models.entities.Weather
import com.ua.weatherwise.domain.models.NetworkResult
import com.ua.weatherwise.domain.usecases.weather.GetByCoordinatesUseCase
import com.ua.weatherwise.domain.usecases.weather.SearchByCityUseCase
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_CITY
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_COORDINATES
import com.ua.weatherwise.utils.Constants.Companion.DEFAULT_UNITS
import com.ua.weatherwise.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getByCoordinatesUseCase: GetByCoordinatesUseCase,
    private val searchByCityUseCase: SearchByCityUseCase,
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
                    _weather.value =
                        getByCoordinatesUseCase.execute(DEFAULT_COORDINATES, DEFAULT_UNITS)
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
                    _weather.value = searchByCityUseCase.execute(DEFAULT_CITY, DEFAULT_UNITS)
                } catch (e: Exception) {
                    _weather.value = NetworkResult.Error("City not found")
                }
            } else {
                _weather.value = NetworkResult.Error("No Internet Connection")
            }
        }
    }
}