package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.view

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class WeatherFragmentFactory @Inject constructor(
    private val glide : RequestManager,
    private val sharedPreferences: SharedPreferences
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            StateFragment::class.java.name -> StateFragment(glide,sharedPreferences)
            ForecastFragment::class.java.name -> ForecastFragment(sharedPreferences,glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}