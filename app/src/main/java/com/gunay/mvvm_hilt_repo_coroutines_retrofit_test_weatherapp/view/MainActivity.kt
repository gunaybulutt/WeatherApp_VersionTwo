package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: WeatherFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)


    }



}