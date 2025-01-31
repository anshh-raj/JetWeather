package com.example.jetweather.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Unit
import com.example.jetweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {
    val unitList = DataOrException(
        data = MutableStateFlow<List<Unit>>(emptyList()),
        loading = true,
        e = Exception("")
    )
//    val unitList = _unitList.data?.asStateFlow()?.value

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getUnits().distinctUntilChanged().collect{ listOfUnit ->
                    unitList.loading = true
                    if(listOfUnit.isEmpty()){
                        Log.d("Units", ": empty list")
                    }else{
                        unitList.data?.value = listOfUnit
                        Log.d("Units", ": ${unitList.data!!.value}")
                    }
                    unitList.loading = false
                }
            } catch (exception: Exception){
                unitList.e = exception
                Log.d("ERRORRR", "e: $exception")
            }
        }
    }

    fun insertUnit(unit: Unit) = viewModelScope.launch {
        repository.insertUnit(unit)
    }

    fun deleteUnit(unit: Unit) = viewModelScope.launch {
        repository.deleteUnit(unit)
    }

    fun deleteAllUnit() = viewModelScope.launch {
        repository.deleteAllUnit()
    }
}