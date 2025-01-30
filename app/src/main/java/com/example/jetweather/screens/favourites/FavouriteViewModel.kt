package com.example.jetweather.screens.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweather.model.Favourites
import com.example.jetweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel(){
    private val _favList = MutableStateFlow<List<Favourites>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavourites().distinctUntilChanged().collect{ listOfFav ->
                if(listOfFav.isEmpty()){
                    Log.d("Favs", ": Empty favs")
                }else{
                    _favList.value = listOfFav
                    Log.d("Favs", ": ${favList.value}")
                }
            }
        }
    }

    fun insertFavourite(favourites: Favourites) = viewModelScope.launch {
        repository.insertFavourite(favourites)
    }

    fun deleteFavourite(favourites: Favourites) = viewModelScope.launch {
        repository.deleteFavourite(favourites)
    }

    fun deleteAllFavourite() = viewModelScope.launch {
        repository.deleteAllFavourites()
    }
}