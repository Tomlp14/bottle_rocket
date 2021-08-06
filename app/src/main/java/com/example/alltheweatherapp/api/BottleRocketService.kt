package com.example.alltheweatherapp.api

import com.example.alltheweatherapp.model.City
import com.example.alltheweatherapp.model.CityDetails
import com.example.alltheweatherapp.model.CitySearchResponse
import com.example.alltheweatherapp.util.Constants.Companion.CITIES
import com.example.alltheweatherapp.util.Constants.Companion.CITY_ID
import com.example.alltheweatherapp.util.Constants.Companion.END_POINT
import com.example.alltheweatherapp.util.Constants.Companion.PAGE_COUNT
import com.example.alltheweatherapp.util.Constants.Companion.PAGE_NUMBER
import com.example.alltheweatherapp.util.Constants.Companion.SEARCH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BottleRocketService {
    @GET(CITIES)
    suspend fun getCityList(
        @Query(SEARCH) city: String
    ): Response<CitySearchResponse>

    @GET(CITIES)
    suspend fun getCityList(
        @Query(SEARCH) city: String,
        @Query(PAGE_COUNT) pageCount: Int,
        @Query(PAGE_NUMBER) pageNumber: Int
    ): Response<CitySearchResponse>

    @GET(END_POINT)
    suspend fun getCityDetails(
        @Path(CITY_ID) cityId: Int
    ): Response<CityDetails>
}