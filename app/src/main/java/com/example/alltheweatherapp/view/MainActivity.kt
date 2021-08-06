package com.example.alltheweatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alltheweatherapp.R
import com.example.alltheweatherapp.databinding.ActivityMainBinding
import com.example.alltheweatherapp.databinding.HomeToolbarBinding
import com.example.alltheweatherapp.di.DI
import com.example.alltheweatherapp.model.CityDetails
import com.example.alltheweatherapp.model.Day
import com.example.alltheweatherapp.model.WeatherRepositoryImpl
import com.example.alltheweatherapp.util.Constants.Companion.CITY_DELETED
import com.example.alltheweatherapp.util.Constants.Companion.CITY_SP
import com.example.alltheweatherapp.util.Constants.Companion.DATE_PATTERN
import com.example.alltheweatherapp.util.Constants.Companion.SAVE_CITIES
import com.example.alltheweatherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), SearchCityFragment.SelectedCity {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var includeBinding: HomeToolbarBinding
    private val viewmodel: WeatherViewModel by lazy{
        WeatherViewModel.WeatherViewModelFactory(DI.provideRepository()).create(
                WeatherViewModel::class.java
        )
    }
    private lateinit var scrollAdapter: WeatherScrollAdapter
    private lateinit var weeklyAdapter: WeatherWeeklyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        includeBinding = activityMainBinding.mainToolbar
        setContentView(activityMainBinding.root)
        initViews()

        viewmodel.cityDetailsState.observe(this){
            when(it){
                is WeatherRepositoryImpl.CityDetailsState.Response -> {
                    updateData(it.cityDetails)
                    showLoading(true)
                }
                is WeatherRepositoryImpl.CityDetailsState.Error -> {
                    showError(it.errorMessage)
                    showLoading(true)
                }
                is WeatherRepositoryImpl.CityDetailsState.Loading -> showLoading(false)
            }
        }
        checkPreviousCities()
    }

    private fun checkPreviousCities() {
        val cities = getSharedPreferences(CITY_SP, MODE_PRIVATE)
                .getStringSet(SAVE_CITIES, emptySet())

        viewmodel.getCityDetails(
                cities?.firstOrNull()?.toInt() ?: 1
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.weather_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                openSearchFragment()
                true
            }
            R.id.delete_city->{
                deleteCurrentCity()
                true
            }
            else-> false
        }
    }

    private fun openSearchFragment() {
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, SearchCityFragment())
                .addToBackStack(null)
                .commit()
    }

    private fun deleteCurrentCity() {
        Toast.makeText(this, CITY_DELETED, Toast.LENGTH_SHORT).show()
    }

    private fun initViews(){
        activityMainBinding.weeklyForecast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        activityMainBinding.forecast.layoutManager = LinearLayoutManager(this)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_icon_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun showLoading(hasData: Boolean) {
        if(hasData) activityMainBinding.progressBar.visibility = View.GONE
        else activityMainBinding.progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun updateData(data: CityDetails) {
        weeklyAdapter = WeatherWeeklyAdapter(data){
            createForecastAdapter(it)
        }
        activityMainBinding.mainToolbar.hometoolbarcity.textCity.text = data.city.name
        activityMainBinding.mainToolbar.hometoolbarcity.textDate.text = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(Date())
        activityMainBinding.mainToolbar.hometoolbarcity.textDegrees.text = "${data.weather.days[0].low.toString()}º"
        activityMainBinding.weeklyForecast.adapter = weeklyAdapter
        Picasso.get().load(data?.city?.imageURLs?.androidImageURL?.xhdpiImageURL).into(activityMainBinding.mainToolbar.currentCity)
    }

    private fun createForecastAdapter(data: Day) {
        scrollAdapter = WeatherScrollAdapter(data)
        activityMainBinding.forecast.adapter = scrollAdapter
    }

    override fun selectedCity(geoId: Int) {
        viewmodel.getCityDetails(geoId)
    }
}