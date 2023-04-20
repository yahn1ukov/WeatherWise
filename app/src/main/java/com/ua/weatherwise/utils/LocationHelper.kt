package com.ua.weatherwise.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.ua.weatherwise.presentation.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class LocationHelper @Inject constructor(
    private val activity: Activity
) {
    @SuppressLint("MissingPermission")
    fun getLocation(weatherViewModel: WeatherViewModel) {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                val locationServices = LocationServices.getFusedLocationProviderClient(activity)
                locationServices.lastLocation.addOnCompleteListener(activity) {
                    it.result?.let { location ->
                        weatherViewModel.setCoordinates(location.latitude, location.longitude)
                    }
                }
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return isGPSEnabled || isNetworkEnabled
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            Constants.PERMISSION_ID
        )
    }
}