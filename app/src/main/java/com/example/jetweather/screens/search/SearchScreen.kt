package com.example.jetweather.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetweather.navigation.WeatherScreens
import com.example.jetweather.widgets.WeatherAppBar

@Composable
fun SearchScreen(navController: NavController){
    Scaffold(
        topBar = {
            WeatherAppBar(
                city = "Search",
                navController = navController,
                isMainScreen = false,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ){
        Surface(
            modifier = Modifier.padding(it)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onSearch = {coordinate->
                        navController.navigate(
                            WeatherScreens.MainScreen.name
                                    +"/${coordinate.split(",")[0]}"
                                    + "/${coordinate.split(",")[1]}"
                                    + "/${coordinate.split(",")[2]}"
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String)-> Unit = {}
){
    val searchCity = rememberSaveable {
        mutableStateOf("")
    }
    val searchLatState = rememberSaveable {
        mutableStateOf("")
    }
    val searchLonState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val validLat = remember(searchLatState.value) {
        searchLatState.value.trim().isNotEmpty()
    }
    val validLon = remember(searchLonState.value) {
        searchLonState.value.trim().isNotEmpty()
    }
    val validCity = remember(searchCity.value) {
        searchCity.value.trim().isNotEmpty()
    }

    Column(
        modifier = modifier
    ) {
        CommonTextField(
            valueState = searchCity,
            placeholder= "City",
            keyboardType = KeyboardType.Text,
            onAction = KeyboardActions {
                if (!validLat || !validLon || !validCity) return@KeyboardActions
                onSearch(searchCity.value.trim()+","+searchLatState.value.trim()+","+searchLonState.value.trim())
                searchCity.value = ""
                searchLatState.value = ""
                searchLonState.value = ""
                keyboardController?.hide()
            }
        )
        CommonTextField(
            valueState = searchLatState,
            placeholder= "Latitude",
            onAction = KeyboardActions {
                if (!validLat || !validLon || !validCity) return@KeyboardActions
                onSearch(searchCity.value.trim()+","+searchLatState.value.trim()+","+searchLonState.value.trim())
                searchCity.value = ""
                searchLatState.value = ""
                searchLonState.value = ""
                keyboardController?.hide()
            }
        )
        CommonTextField(
            valueState = searchLonState,
            placeholder= "Longitude",
            onAction = KeyboardActions {
                if(!validLat || !validLon || !validCity) return@KeyboardActions
                onSearch(searchCity.value.trim()+","+searchLatState.value.trim()+","+searchLonState.value.trim())
                searchCity.value = ""
                searchLatState.value = ""
                searchLonState.value = ""
                keyboardController?.hide()
            }
        )
    }

}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = {
            Text(text = placeholder)
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
}
