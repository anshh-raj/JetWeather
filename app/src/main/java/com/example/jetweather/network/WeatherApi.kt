package com.example.jetweather.network


import com.example.jetweather.model.Weather
import com.example.jetweather.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/3.0/onecall")
    suspend fun getWeather(
        @Query("lat") latitude:String,
        @Query("lon") longitude:String,
        @Query("exclude") exclude:String = "current,minutely,hourly,alerts",
        @Query("units") units:String = "metric",
        @Query("appid") appid:String = API_KEY
    ) : Weather
}