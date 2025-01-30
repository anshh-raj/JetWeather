package com.example.jetweather.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetweather.model.Favourites
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM FAV_TBL")
    fun getFavourites(): Flow<List<Favourites>>

    @Query("SELECT * FROM FAV_TBL WHERE city =:city")
    suspend fun getFavById(city: String): Favourites

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourites: Favourites)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favourites: Favourites)

    @Query("DELETE FROM FAV_TBL")
    suspend fun deleteAllFavourite()

    @Delete
    suspend fun deleteFavourite(favourites: Favourites)
}