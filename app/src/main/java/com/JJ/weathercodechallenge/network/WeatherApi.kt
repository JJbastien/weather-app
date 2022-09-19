package com.JJ.weathercodechallenge.network

import com.JJ.weathercodechallenge.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(FORECAST_WEATHER)
    suspend fun retrieveCityWeather(
        @Query("q") city: String,
        @Query("appid") appId: String = "65d00499677e59496ca2f318eb68c049"
    ): Response<WeatherResponse>

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        private const val FORECAST_WEATHER = "data/2.5/forecast"
    }
}