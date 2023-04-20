package com.ua.weatherwise.presentation.weather.screens

import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import com.ua.weatherwise.R
import com.ua.weatherwise.data.remote.models.entities.Weather
import com.ua.weatherwise.databinding.ActivityMainBinding
import com.ua.weatherwise.presentation.weather.viewmodels.WeatherViewModel
import com.ua.weatherwise.utils.Constants.Companion.PERMISSION_ID
import com.ua.weatherwise.utils.LocationHelper
import com.ua.weatherwise.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val weatherViewModel: WeatherViewModel by viewModels()

    private val locationHelper: LocationHelper by lazy { LocationHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.activity_weather_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.menu_location) {
                    locationHelper.getLocation(weatherViewModel)
                }
                return true
            }
        })

        weatherViewModel.getCoordinates.observe(this) {
            fetchWeather(it.latitude, it.longitude)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                locationHelper.getLocation(weatherViewModel)
            }
        }
    }

    private fun fetchWeather(latitude: Double, longitude: Double) {
        weatherViewModel.fetchWeatherByCoordinates(latitude, longitude)
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