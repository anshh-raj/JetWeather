package com.example.jetweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetweather.model.Favourites
import com.example.jetweather.model.Unit

@Database(entities = [Favourites::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase(){
    abstract fun weatherDao(): WeatherDao
}