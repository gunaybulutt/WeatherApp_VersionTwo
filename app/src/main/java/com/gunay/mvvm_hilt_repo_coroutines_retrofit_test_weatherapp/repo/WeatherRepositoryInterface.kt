package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.repo

import androidx.lifecycle.LiveData
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.model.WeatherData
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb.RoomModel


interface WeatherRepositoryInterface {

    suspend fun insertWeather(cityName : RoomModel)

    suspend fun deleteWeather(cityName: RoomModel)

    fun getAll(): LiveData<List<RoomModel>>

    suspend fun getData(cityName : String) : WeatherData?

}