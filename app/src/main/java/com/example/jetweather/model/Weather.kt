package com.example.jetweather.model

data class Weather(
    val lat: Double,
    val lon: Double,
    val daily: List<Daily>,
    val timezone: String,
    val timezone_offset: Int
)