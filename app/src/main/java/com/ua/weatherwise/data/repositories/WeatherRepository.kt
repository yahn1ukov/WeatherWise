package com.ua.weatherwise.data.repositories

import com.ua.weatherwise.data.remote.repositories.WeatherRemoteDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class WeatherRepository @Inject constructor(
    weatherRemoteDataSource: WeatherRemoteDataSource
) {
    val remote = weatherRemoteDataSource
}