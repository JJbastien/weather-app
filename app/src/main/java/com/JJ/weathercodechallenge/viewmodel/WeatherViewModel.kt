package com.JJ.weathercodechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.JJ.weathercodechallenge.model.Forecast
import com.JJ.weathercodechallenge.model.WeatherResponse
import com.JJ.weathercodechallenge.utils.*
import com.JJ.weathercodechallenge.weatherusecase.WeatherResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): BaseViewModel() {

    var isError = false

    private val currentWeatherResult by lazy {
        WeatherResult(viewModelScopeSafe, ioDispatcher)
    }

    private var _currentWeather: MutableLiveData<Result<WeatherResponse, Failure>> = MutableLiveData()
    val currentWeather: LiveData<Result<WeatherResponse, Failure>> get() = _currentWeather

    private var _forecastDetails: MutableLiveData<Result<Forecast, Failure>> = MutableLiveData()
    val forecastDetails: LiveData<Result<Forecast, Failure>> get() = _forecastDetails

    fun loadCurrentLocationWeather(cityName: String) {
        _currentWeather.postValue(Result.loading())
        collectWeather()
        currentWeatherResult.getCurrentCityWeather(cityName)
    }

    private fun collectWeather() {
        viewModelScopeSafe.launch {
            currentWeatherResult.currentCityWeather.collect { result ->
                result
                    .onLoading { _currentWeather.postValue(Result.loading()) }
                    .onSuccess { _currentWeather.postValue(Result.success(it)) }
                    .onFailure { _currentWeather.postValue(Result.failure(it)) }
            }
        }
    }

    fun reset() {
        _currentWeather.postValue(Result.loading())
    }

    fun passForecastDetails(forecast: Forecast) {
        _forecastDetails.postValue(Result.success(forecast))
    }

}