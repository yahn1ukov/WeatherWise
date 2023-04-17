package com.ua.weatherwise.data.remote.models.entities


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)