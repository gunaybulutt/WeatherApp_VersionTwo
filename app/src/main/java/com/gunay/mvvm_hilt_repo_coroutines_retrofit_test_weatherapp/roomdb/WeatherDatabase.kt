package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [RoomModel::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao() : WeatherDao

}