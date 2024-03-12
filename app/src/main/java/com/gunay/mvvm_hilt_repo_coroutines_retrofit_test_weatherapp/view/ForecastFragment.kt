package com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.adapter.ForecastAdapter
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.databinding.FragmentForecastBinding
import com.gunay.mvvm_hilt_repo_coroutines_retrofit_test_weatherapp.viewmodel.ForecastPageVM
import javax.inject.Inject


class ForecastFragment @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    val glide : RequestManager,
) : Fragment() {

    private lateinit var binding: FragmentForecastBinding
    private var forecastAdapter : ForecastAdapter? = null
    private lateinit var viewModel : ForecastPageVM

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedCity = forecastAdapter!!.recyclerData[layoutPosition]
            viewModel.deleteCity(selectedCity)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ForecastPageVM::class.java)


        forecastAdapter = ForecastAdapter(sharedPreferences,glide)
        val layoutManager = LinearLayoutManager(context).apply {
            reverseLayout = true
            stackFromEnd = true
        }

        // RecyclerView'ı odakla
        layoutManager.getChildAt(0)?.requestFocus()
        binding.forecastRecyclerView.layoutManager = layoutManager
        binding.forecastRecyclerView.adapter = forecastAdapter
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.forecastRecyclerView)
        subscribeToObserves()

        binding.forecastRecyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            layoutManager.scrollToPositionWithOffset(binding.forecastRecyclerView.adapter!!.itemCount, 0)
        }



        binding.searchButton.setOnClickListener {

            sharedPreferences.edit().putString("showingCity", binding.searchText.text.toString()).apply()


            val action = ForecastFragmentDirections.actionForecastFragmentToStateFragment()
            Navigation.findNavController(it).navigate(action)

        }

    }

    fun subscribeToObserves(){
        viewModel.weatherList.observe(viewLifecycleOwner, Observer {
            forecastAdapter?.recyclerData = it
            binding.forecastRecyclerView.scrollToPosition(it.size-1)
        })
    }

}