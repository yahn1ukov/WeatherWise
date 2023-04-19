package com.ua.weatherwise.utils

class Constants {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val ICON_URL = "https://openweathermap.org/img/wn"

        const val ENDPOINT_WEATHER = "weather"

        const val QUERY_LATITUDE = "lat"
        const val QUERY_LONGITUDE = "lon"
        const val QUERY_API_KEY = "appid"
        const val QUERY_UNITS = "units"

        const val API_KEY = ""
        val DEFAULT_COORDINATES = doubleArrayOf(51.5085, -0.1257)
        const val DEFAULT_UNITS = "Metric"

        const val PERMISSION_ID = 2
    }
}