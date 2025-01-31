package com.example.jetweather.screens.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweather.model.Unit
import com.example.jetweather.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController, settingsViewModel: SettingsViewModel = hiltViewModel()){

    val choiceFromDbMain = settingsViewModel.unitList
    if(choiceFromDbMain.loading == false){

        val choiceFromDb = choiceFromDbMain.data!!.collectAsState().value

        val measurementsUnits = listOf("Metric (C)", "Imperial (F)")

        val defaultBoolChoice = if(choiceFromDb.isEmpty()) false
        else if(choiceFromDb[0].unit == measurementsUnits[0]) false
        else true

        Log.d("Book Value", "SettingsScreen: $defaultBoolChoice")

        var unitToggleState by remember {
            mutableStateOf(defaultBoolChoice)
        }

        val defaultChoice = if(choiceFromDb.isEmpty()) measurementsUnits[0]
        else choiceFromDb[0].unit
        var choiceState by remember {
            mutableStateOf(defaultChoice)
        }

        Log.d("Book Value", "SettingsScreen: $defaultChoice")

        Scaffold(
            topBar = {
                WeatherAppBar(
                    city = "Settings",
                    navController = navController,
                    isMainScreen = false,
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    onButtonClicked = {
                        navController.popBackStack()
                    }
                )
            }
        ) {
            Surface(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Change Units of Measurement",
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                    )

                    IconToggleButton(
                        checked = unitToggleState,
                        onCheckedChange = {
                            unitToggleState = it
                            if(unitToggleState){
                                choiceState = "Imperial (F)"
                            }
                            else{
                                choiceState = "Metric (C)"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .clip(RectangleShape)
                            .padding(5.dp)
                            .background(Color.Magenta.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = if (unitToggleState)
                                "Fahrenheit °F"
                            else
                                "Celsius °C",
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = {
                            settingsViewModel.deleteAllUnit()
                            settingsViewModel.insertUnit(
                                Unit(
                                    unit = choiceState
                                )
                            )
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(34.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEFBE42)
                        )
                    ) {
                        Text(
                            "Save",
                            modifier = Modifier.padding(4.dp),
                            color = Color.White,
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }
    }
}