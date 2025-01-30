package com.example.jetweather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tbl")
data class Favourites(
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "latitude")
    val lat: String,

    @ColumnInfo(name = "longitude")
    val lon: String
)
