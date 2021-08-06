package com.example.alltheweatherapp.util

class Constants {

    companion object{
        //Retrofit
        const val BASE_URL = "https://weather.exam.bottlerocketservices.com/"
        const val CITIES= "cities"
        const val SEARCH= "search"
        const val PAGE_COUNT= "pageCount"
        const val PAGE_NUMBER="pageNumber"
        const val CITY_ID="cityId"
        const val END_POINT="cities/{$CITY_ID}"
       //Error message
        const val CITY_MSN="No city has founded."
        //Shared preferences
        const val CITY_SP="WeatherRepositoryImpl_CITY_SP"
        const val SAVE_CITIES="WeatherRepositoryImpl_SAVED_CITIES"
        //Tost messages
        const val CITY_DELETED="Current city deleted"
       //Date pattern
        const val DATE_PATTERN="E MM/dd/yy hh:mm a"

        //SearchCityFragment
        const val TAG = "SearchCityFragment"

        //WeatherScrillAdapter
        const val SUNNY ="sunny"
        const val CLOUDY="cloudy"
        const val HEAVY_RAIN="heavyRain"
        const val LIGHT_RAIN="lightRain"
        const val PARTLY_CLOUDY="partlyCloudy"
        const val SNOW_SLEET="snowSleet"
        //WeatherWeeklyAdapter
        const val MON= "MON"
        const val TUE= "TUE"
        const val WED= "WED"
        const val THUR="THUR"
        const val FRI="FRI"
        const val SAT="SAT"
        const val SUN="SUN"


    }

}