package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.model.WeatherData
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.repo.WeatherRepositoryInterface
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb.RoomModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StatePageVM @Inject constructor(
    private val repository : WeatherRepositoryInterface
)  : ViewModel() {


    var cityData = MutableLiveData<WeatherData?>()
    var roomModel : RoomModel? = null


    fun getDataFromAPI(cityName : String){
            viewModelScope.launch {
                val response = repository.getData(cityName)
                cityData.value = response
            }


    }

    fun kelvinToCelcious(weatherData: WeatherData) : String{
        val celcious = weatherData.main.temp - 273.15
        val string_celcious = String.format("%.1f", celcious)

        return string_celcious
    }

    fun saveDatabase(cityName: String, icon : String, date : String){
        roomModel = RoomModel(cityName,icon, date)
        viewModelScope.launch {

                repository.insertWeather(roomModel!!)

        }

    }

    fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd   HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }


}