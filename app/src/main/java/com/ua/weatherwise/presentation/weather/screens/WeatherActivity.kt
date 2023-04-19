package com.ua.weatherwise.presentation.weather.screens

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ua.weatherwise.R
import com.ua.weatherwise.data.remote.models.entities.Weather
import com.ua.weatherwise.databinding.ActivityMainBinding
import com.ua.weatherwise.presentation.weather.viewmodels.WeatherViewModel
import com.ua.weatherwise.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fetchWeather()
    }

    private fun fetchWeather() {
        weatherViewModel.fetchWeatherByCoordinates()
        weatherViewModel.weather.observe(this) { response ->
            handleUIState(response)
        }
    }

    private fun handleUIState(response: NetworkResult<Weather>) {
        when (response) {
            is NetworkResult.Success -> {
                binding.componentLoading.root.visibility = View.GONE
                binding.componentSuccess.root.visibility = View.VISIBLE
                response.data?.let { weather ->
                    binding.componentSuccess.weather = weather
                }
            }

            is NetworkResult.Error -> {
                binding.componentLoading.root.visibility = View.GONE
                binding.componentError.root.visibility = View.VISIBLE
                response.message?.let { message ->
                    binding.componentError.message = message
                }
            }

            is NetworkResult.Loading -> {
                binding.componentLoading.root.visibility = View.VISIBLE
            }
        }
    }
}