package com.example.jetweather.di

import com.example.jetweather.network.WeatherApi
import com.example.jetweather.repository.WeatherRepository
import com.example.jetweather.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi) = WeatherRepository(api)

    @Provides
    @Singleton
    fun provideOpenWeatherApi():WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}