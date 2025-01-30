package com.example.jetweather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetweather.screens.about.AboutScreen
import com.example.jetweather.screens.favourites.FavouritesScreen
import com.example.jetweather.screens.main.MainViewModel
import com.example.jetweather.screens.main.WeatherMainScreen
import com.example.jetweather.screens.search.SearchScreen
import com.example.jetweather.screens.settings.SettingsScreen
import com.example.jetweather.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}/{latitude}/{longitude}",
            arguments = listOf(
                navArgument(name = "city"){type = NavType.StringType},
                navArgument(name = "latitude"){type = NavType.StringType},
                navArgument(name = "longitude"){type = NavType.StringType}
            )
        ){navBack ->
//            navBack.arguments?.getString("coordinate").let {coordinate->
//                val mainViewModel = hiltViewModel<MainViewModel>()
//                WeatherMainScreen(navController, mainViewModel, coordinate = coordinate)
//            }
            val city = navBack.arguments?.getString("city")
            val latitude = navBack.arguments?.getString("latitude")
            val longitude = navBack.arguments?.getString("longitude")
            val mainViewModel = hiltViewModel<MainViewModel>()
            WeatherMainScreen(navController, mainViewModel,city = city, latitude = latitude, longitude = longitude)
        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController)
        }

        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController)
        }

        composable(WeatherScreens.FavouriteScreen.name){
            FavouritesScreen(navController)
        }

        composable(WeatherScreens.SettingsScreen.name){
            SettingsScreen(navController)
        }
    }
}