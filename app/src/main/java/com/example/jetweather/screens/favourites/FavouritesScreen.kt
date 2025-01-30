package com.example.jetweather.screens.favourites

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweather.model.Favourites
import com.example.jetweather.navigation.WeatherScreens
import com.example.jetweather.widgets.WeatherAppBar

@Composable
fun FavouritesScreen(navController: NavController, favouriteViewModel: FavouriteViewModel = hiltViewModel()){
    Scaffold(
        topBar = {
            WeatherAppBar(
                city = "Favourites Cities",
                isMainScreen = false,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                navController = navController,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Surface (
            modifier = Modifier
                .padding(it)
                .padding(5.dp)
                .fillMaxWidth()
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = favouriteViewModel.favList.collectAsState().value

                LazyColumn {
                    items(list){favourite->
                        CityRow(favourite, navController, favouriteViewModel)
                    }
                }

            }

        }
    }
}

@Composable
fun CityRow(favourite: Favourites, navController: NavController, favouriteViewModel: FavouriteViewModel) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(50.dp)
            .clickable {
                navController.navigate(
                    WeatherScreens.MainScreen.name
                    +"/${favourite.city}"
                    +"/${favourite.lat}"
                    +"/${favourite.lon}"
                )
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                favourite.city,
                modifier = Modifier.padding(start = 4.dp)
            )
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    favourite.lat,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    favourite.lon,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .clickable {
                        favouriteViewModel.deleteFavourite(favourite)
                        Toast.makeText(context,"City Deleted", Toast.LENGTH_SHORT).show()
                    },
                tint = Color.Red.copy(alpha = 0.3f)
            )
        }
    }
}
