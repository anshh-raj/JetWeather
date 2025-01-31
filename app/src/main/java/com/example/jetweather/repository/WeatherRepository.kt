package com.example.jetweather.repository

import android.util.Log
import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Weather
import com.example.jetweather.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(latitude: String, longitude: String, units: String):
            DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(latitude = latitude, longitude = longitude, units = units)
        }catch (exception: Exception){
            Log.d("ERRORP", "getWeather: $exception")
            return DataOrException(e = exception)
        }
        return DataOrException(data = response)
    }
}