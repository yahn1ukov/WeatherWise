package com.ua.weatherwise.data.repositories

import com.ua.weatherwise.data.remote.repositories.WeatherRemoteDataSource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    weatherRemoteDataSource: WeatherRemoteDataSource
) {
    val remote = weatherRemoteDataSource
}