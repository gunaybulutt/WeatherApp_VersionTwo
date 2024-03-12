package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.dependencyinjection

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.R
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.repo.WeatherRepository
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.repo.WeatherRepositoryInterface
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb.WeatherDao
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb.WeatherDatabase
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.service.WeatherAPI
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRetrofitService() : WeatherAPI{

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Util.BASE_URL)
            .build()
            .create(WeatherAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(api: WeatherAPI, dao : WeatherDao) = WeatherRepository(api,dao) as WeatherRepositoryInterface


    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )


    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, WeatherDatabase::class.java,"WeatherDB").build()

    @Singleton
    @Provides
    fun injectDao(database: WeatherDatabase) = database.weatherDao()

    @Singleton
    @Provides
    fun injectSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("weather_app_prefs", Context.MODE_PRIVATE)
    }

}