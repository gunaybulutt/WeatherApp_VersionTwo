package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(cityName: RoomModel)

    @Delete
    suspend fun delete(cityName: RoomModel)

    @Query("SELECT * FROM City")
    fun observeWeather(): LiveData<List<RoomModel>>

}