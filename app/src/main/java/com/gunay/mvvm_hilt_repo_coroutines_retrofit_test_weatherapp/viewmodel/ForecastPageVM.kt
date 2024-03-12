package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.repo.WeatherRepository
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb.RoomModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastPageVM @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: WeatherRepository
): ViewModel() {

    val weatherList = repository.getAll()

    fun insertCity(cityName : RoomModel){
        viewModelScope.launch {
            repository.insertWeather(cityName)
        }
    }

    fun deleteCity(cityName: RoomModel){
        viewModelScope.launch {
            repository.deleteWeather(cityName)
        }
    }

}