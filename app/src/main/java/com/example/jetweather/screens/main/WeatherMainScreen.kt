package com.example.jetweather.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Weather


@Composable
fun WeatherMainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()){

    val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
        initialValue = DataOrException(loading = true)){
        value = mainViewModel.getWeatherData(
            lat = "28.7041",
            lon = "77.1025"
        )
    }.value

    if(weatherData.loading == true){
        CircularProgressIndicator()
    }
    else if (weatherData.data != null){
        MainScaffold(weatherData.data!!, navController)

    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(
        topBar = {

        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            MainContent(data = weather)
        }
    }

}

@Composable
fun MainContent(data: Weather) {
    Text(data.timezone)
}
