package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.adapter


import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.databinding.WeatherRowBinding
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.roomdb.RoomModel
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.view.ForecastFragmentDirections
import javax.inject.Inject

class ForecastAdapter @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    val glide : RequestManager
) :RecyclerView.Adapter<ForecastAdapter.WeatherHolder>() {

    class WeatherHolder(val binding : WeatherRowBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<RoomModel>() {
        override fun areItemsTheSame(oldItem: RoomModel, newItem: RoomModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RoomModel, newItem: RoomModel): Boolean {
            return oldItem == newItem
        }


    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var recyclerData: List<RoomModel>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val binding = WeatherRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherHolder(binding)
    }

    override fun getItemCount(): Int {
        return recyclerData.size
    }



    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.binding.weatherRowCityName.text = recyclerData[position].CityName
        holder.binding.RowDate.text = "Date: ${recyclerData[position].date}"
        glide.load(recyclerData[position].icon).into(holder.binding.rowImageView)



        holder.itemView.setOnClickListener{


            sharedPreferences.edit().putString("showingCity",recyclerData[position].CityName).apply()


            val action = ForecastFragmentDirections.actionForecastFragmentToStateFragment()
            Navigation.findNavController(it).navigate(action)



        }
    }



}