package com.example.jetweather.repository

import com.example.jetweather.data.WeatherDao
import com.example.jetweather.model.Favourites
import com.example.jetweather.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavourites(): Flow<List<Favourites>> = weatherDao.getFavourites()
    suspend fun getFavById(city: String) = weatherDao.getFavById(city)
    suspend fun insertFavourite(favourites: Favourites) = weatherDao.insertFavourite(favourites)
    suspend fun updateFavourite(favourites: Favourites) = weatherDao.updateFavourite(favourites)
    suspend fun deleteAllFavourites() = weatherDao.deleteAllFavourite()
    suspend fun deleteFavourite(favourites: Favourites) = weatherDao.deleteFavourite(favourites)

    // for unit

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnit()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnit() = weatherDao.deleteAllUnit()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)

}