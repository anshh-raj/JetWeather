package com.example.jetweather.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweather.model.Favourites
import com.example.jetweather.navigation.WeatherScreens
import com.example.jetweather.screens.favourites.FavouriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    city: String = "",
    lat: String = "",
    lon: String = "",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClicked: ()-> Unit = {},
    onButtonClicked: ()-> Unit = {}
){
    val showDialog = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    if (showDialog.value){
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }
    CenterAlignedTopAppBar(
        title = { Row{
            Text(
                city,
//                color = MaterialTheme.colorScheme.onSecondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
            Text(
                text = if(lat.isNotEmpty())" , $lat" else "",
//                color = MaterialTheme.colorScheme.onSecondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
            Text(
                text = if(lon.isNotEmpty())" , $lon" else "",
//                color = MaterialTheme.colorScheme.onSecondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        } },
        actions = {
            if(isMainScreen){
                IconButton(
                    onClick = {
                        onAddActionClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search"
                    )
                }
                IconButton(
                    onClick = {
                        showDialog.value = !showDialog.value
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More Icon"
                    )
                }
            }
            else{
                Box{}
            }
        },
        navigationIcon = {
            if (icon != null){
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }
            if(isMainScreen){
                val isAlreadyFavList = favouriteViewModel.favList.collectAsState().value
                    .filter { item->
                        item.city == city
                    }
                if(isAlreadyFavList.isEmpty()){
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourite icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                favouriteViewModel.insertFavourite(
                                    Favourites(
                                        city = city,
                                        lat = lat,
                                        lon = lon
                                    )
                                )
                                Toast.makeText(context,"Added to Favourites", Toast.LENGTH_SHORT).show()
                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                }
                else {
                    Box{}
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf("About","Favourites","Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }, text = {
                    Row(

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when(text){
                                "About" -> Icons.Default.Info
                                "Favourites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            },
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                        Text(
                            text,
                            modifier = Modifier.clickable {
                                navController.navigate(when(text){
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favourites" -> WeatherScreens.FavouriteScreen.name
                                    else -> WeatherScreens.SettingsScreen.name
                                })
                            },
                            fontWeight = FontWeight.W400
                        )
                    }
                })
            }
        }

    }
}
