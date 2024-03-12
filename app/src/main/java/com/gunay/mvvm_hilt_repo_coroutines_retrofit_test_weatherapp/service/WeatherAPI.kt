package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.service

import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.model.WeatherData
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.util.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = Util.API_KEY
    ): Response<WeatherData>

}