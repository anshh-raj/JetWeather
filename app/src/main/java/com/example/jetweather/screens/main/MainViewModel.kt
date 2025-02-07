package com.example.jetweather.screens.main

import androidx.lifecycle.ViewModel
import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Weather
import com.example.jetweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {
    suspend fun getWeatherData(lat: String, lon: String, units: String):
            DataOrException<Weather,Boolean,Exception>{
        return repository.getWeather(
            latitude = lat,
            longitude = lon,
            units = units
        )
    }
}