package com.ua.weatherwise.utils

class Constants {
    companion object {
        const val DATA_STORE = "WeatherWise"
        const val PREFERENCE_COORDINATES = "coordinates"
        const val PREFERENCE_UNITS = "units"

        const val BASE_URL = "https://api.openweathermap.org/data/2.5"
        const val API_KEY = ""
        const val ENDPOINT_WEATHER = "/weather"
        const val ICON_URL = "http://openweathermap.org/img/w/"

        const val QUERY_LATITUDE = "lat"
        const val QUERY_LONGITUDE = "lon"
        const val QUERY_CITY = "q"
        const val QUERY_API_KEY = "appid"
        const val QUERY_UNITS = "units"

        val DEFAULT_COORDINATES = doubleArrayOf(-0.1257, 51.5085)
        const val DEFAULT_CITY = "London"
        const val DEFAULT_UNITS = "Metric"

        const val PERMISSION_ID = 2
    }
}