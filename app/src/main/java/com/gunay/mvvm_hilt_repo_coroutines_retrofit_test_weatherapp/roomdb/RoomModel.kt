package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "City")
data class RoomModel(

    var CityName : String ,
    var icon : String,
    var date : String,


    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
)