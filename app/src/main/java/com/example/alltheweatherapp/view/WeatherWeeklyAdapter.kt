package com.example.alltheweatherapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alltheweatherapp.R
import com.example.alltheweatherapp.databinding.WeeklyWeatherLayoutBinding
import com.example.alltheweatherapp.model.CityDetails
import com.example.alltheweatherapp.model.Day
import com.example.alltheweatherapp.util.Constants
import com.example.alltheweatherapp.util.Constants.Companion.CLOUDY
import com.example.alltheweatherapp.util.Constants.Companion.FRI
import com.example.alltheweatherapp.util.Constants.Companion.HEAVY_RAIN
import com.example.alltheweatherapp.util.Constants.Companion.LIGHT_RAIN
import com.example.alltheweatherapp.util.Constants.Companion.MON
import com.example.alltheweatherapp.util.Constants.Companion.PARTLY_CLOUDY
import com.example.alltheweatherapp.util.Constants.Companion.SAT
import com.example.alltheweatherapp.util.Constants.Companion.SNOW_SLEET
import com.example.alltheweatherapp.util.Constants.Companion.SUN
import com.example.alltheweatherapp.util.Constants.Companion.SUNNY
import com.example.alltheweatherapp.util.Constants.Companion.THUR
import com.example.alltheweatherapp.util.Constants.Companion.TUE
import com.example.alltheweatherapp.util.Constants.Companion.WED
import com.squareup.picasso.Picasso

class WeatherWeeklyAdapter(private val dataset: CityDetails,
    private val callback: (Day)->Unit): RecyclerView.Adapter<WeatherWeeklyAdapter.WeatherWeeklyViewHolder>() {

    class WeatherWeeklyViewHolder(private val binding: WeeklyWeatherLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(dataItem: Day, callback:(Day)-> Unit){
            binding.root.setOnClickListener {
                callback.invoke(dataItem)
            }
            binding.weeklyDay.text =
                when(dataItem.dayOfTheWeek){
                    0-> MON
                    1-> TUE
                    2-> WED
                    3-> THUR
                    4-> FRI
                    5-> SAT
                    else-> SUN
                }
            binding.weeklyTemp.text = dataItem.low.toString()

            when(dataItem.weatherType){
                SUNNY ->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_sunny_active)
                            .into(binding.weeklyIcon)
                CLOUDY->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_cloudy_active)
                            .into(binding.weeklyIcon)
                HEAVY_RAIN->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_heavy_rain_active)
                            .into(binding.weeklyIcon)
                LIGHT_RAIN->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_light_rain_active)
                            .into(binding.weeklyIcon)
                PARTLY_CLOUDY->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_partly_cloudy_active)
                            .into(binding.weeklyIcon)
                SNOW_SLEET->
                    Picasso.get().load(R.drawable.ic_icon_weather_active_ic_snow_sleet_active)
                            .into(binding.weeklyIcon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            WeatherWeeklyViewHolder(WeeklyWeatherLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            ))

    override fun onBindViewHolder(holder: WeatherWeeklyViewHolder, position: Int) {
        holder.onBind(dataset.weather.days[position], callback)
    }

    override fun getItemCount() = dataset.weather.days.size
}