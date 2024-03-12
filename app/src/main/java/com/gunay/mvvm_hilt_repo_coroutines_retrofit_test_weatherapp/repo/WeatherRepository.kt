package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.repo

import androidx.lifecycle.LiveData
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.model.WeatherData
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb.RoomModel
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb.WeatherDao
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.service.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherAPI: WeatherAPI,
    private val weatherDao : WeatherDao
) : WeatherRepositoryInterface {

        private var repoReto : WeatherData? = null

    override suspend fun insertWeather(cityName: RoomModel) {
        weatherDao.insertWeather(cityName)
    }

    override suspend fun deleteWeather(cityName: RoomModel) {
        weatherDao.delete(cityName)
    }

    override fun getAll(): LiveData<List<RoomModel>> {
        return weatherDao.observeWeather()
    }


    //daha onceden apiservice adı altında yaptığım buildden sonraki fonksiyonu burada yapmadan direk interfaceye erişiyorz
        override suspend fun getData(cityName : String) : WeatherData? {
            val response = weatherAPI.getWeather(cityName)
            if (response.isSuccessful) {
                response.body()?.let {
                    repoReto = it
                }

            }
            return repoReto

        }
    }