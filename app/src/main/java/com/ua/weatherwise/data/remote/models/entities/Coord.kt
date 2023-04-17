package com.ua.weatherwise.data.remote.models.entities


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    val lat: Int,
    @SerializedName("lon")
    val lon: Double
)