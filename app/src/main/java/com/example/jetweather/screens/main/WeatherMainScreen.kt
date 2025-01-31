package com.example.jetweather.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Weather
import com.example.jetweather.navigation.WeatherScreens
import com.example.jetweather.screens.settings.SettingsViewModel
import com.example.jetweather.utils.formatDate
import com.example.jetweather.utils.formatDecimals
import com.example.jetweather.widgets.HumidityWindPressureRow
import com.example.jetweather.widgets.SunsetSunriseRow
import com.example.jetweather.widgets.WeatherAppBar
import com.example.jetweather.widgets.WeatherDetailRow
import com.example.jetweather.widgets.WeatherStateImage


@Composable
fun WeatherMainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    latitude: String?,
    longitude: String?,
    city: String?
){
    val unitFromDB = settingsViewModel.unitList
    if(unitFromDB.loading == false){
        val unitFromDb = unitFromDB.data!!.collectAsState().value

        var unit by remember {
            mutableStateOf("metric")
        }

        var isMetric by remember {
            mutableStateOf(false)
        }

        unit = if (unitFromDb.isNotEmpty()) unitFromDb[0].unit.split(" ")[0].lowercase()
        else "metric"
        Log.d("unitt", "WeatherMainScreen: $unit")
        isMetric = unit == "metric"
        Log.d("Coordinate", "WeatherMainScreen: $latitude $longitude")

        val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
            initialValue = DataOrException(loading = true)){
            value = mainViewModel.getWeatherData(
//            lat = "28.7041",
//            lon = "77.1025"
                lat = latitude.toString(),
                lon = longitude.toString(),
                units = unit
            )
        }.value

        if(weatherData.loading == true){
            CircularProgressIndicator()
        }
        else if (weatherData.data != null){
            MainScaffold(weatherData.data!!, navController, city, isMetric = isMetric)

        }
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, city: String?, isMetric: Boolean) {

    Scaffold(
        topBar = {
            WeatherAppBar(
                city = city.toString(),
                lat = weather.lat.toString(),
                lon = weather.lon.toString(),
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            ){
                Log.d("TAG", "MainScaffold: ButtonClicked")
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            MainContent(data = weather, isMetric = isMetric)
        }
    }

}

@Composable
fun MainContent(data: Weather, isMetric: Boolean) {
    val ImageUrl = "https://openweathermap.org/img/wn/${data.daily[0].weather[0].icon}.png"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(data.daily[0].dt),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = ImageUrl)
                Text(
                    text = formatDecimals(data.daily[0].temp.day) + "°",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = data.daily[0].weather[0].main,
                    fontStyle = FontStyle.Italic,
                )
            }
        }
        HumidityWindPressureRow(weather = data, isMetric = isMetric)
        HorizontalDivider()
        SunsetSunriseRow(weather = data)
        Text(
            "This Week",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(data.daily){weatherItem ->
                    WeatherDetailRow(weather = weatherItem)
                }
            }
        }
    }
}

