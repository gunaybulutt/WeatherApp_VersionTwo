package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.model


data class WeatherData(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)
