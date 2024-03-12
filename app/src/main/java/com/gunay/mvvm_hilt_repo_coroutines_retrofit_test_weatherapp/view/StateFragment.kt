package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.RequestManager
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.databinding.FragmentStateBinding
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.util.Util
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.viewmodel.StatePageVM
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class StateFragment @Inject constructor(
    val glide : RequestManager,
    val sharedPreferences: SharedPreferences
) : Fragment() {

    private lateinit var binding : FragmentStateBinding
    private lateinit var viewModel : StatePageVM
    private var cityName : String = ""
    private var date : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(StatePageVM::class.java)



        cityName = sharedPreferences.getString("showingCity", null).toString()
        viewModel = ViewModelProvider(this).get(StatePageVM::class.java)
        viewModel.getDataFromAPI(cityName.toString())



        observeLiveData()

        binding.backButton.setOnClickListener {
            val action = StateFragmentDirections.actionStateFragmentToForecastFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    private fun observeLiveData(){
        viewModel.cityData.observe(viewLifecycleOwner, Observer{
            if(it != null){
                binding.cityNameText.text = it.name
                binding.forecastText.text = it.weather.firstOrNull()?.main
                binding.celciustText.text = "${viewModel.kelvinToCelcious(it)}"
                glide.load("${Util.iconUrl + it.weather.firstOrNull()?.icon}.png").into(binding.imageView)
                var cityIcon = "${Util.iconUrl + it.weather.firstOrNull()?.icon}.png"
                date = dateGetter()
                saveData(cityName.toUpperCase(Locale.ROOT),cityIcon, date)
            }
        })
    }

    private fun dateGetter() : String{
        return viewModel.getCurrentDateTime()
    }

    private fun saveData(data : String,icon : String, date : String){
        viewModel.saveDatabase(data, icon, date)
    }

}





