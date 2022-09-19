package com.JJ.weathercodechallenge.weatherusecase

import com.JJ.weathercodechallenge.model.WeatherResponse
import com.JJ.weathercodechallenge.utils.Failure
import com.JJ.weathercodechallenge.utils.Result
import com.JJ.weathercodechallenge.utils.onFailure
import com.JJ.weathercodechallenge.utils.onSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherResult(
    coroutineScope: CoroutineScope,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CoroutineScope by coroutineScope {

    private val _currentWeather by lazy {
        MutableStateFlow<Result<WeatherResponse, Failure>>(Result.loading())
    }
    val currentCityWeather: StateFlow<Result<WeatherResponse, Failure>>
        get() = _currentWeather

    internal fun getCurrentCityWeather(city: String) {
        val currentWeatherUseCase = WeatherUseCaseImpl(city)
        _currentWeather.value = Result.loading()

        launch(ioDispatcher) {
            val currentWeatherResult = currentWeatherUseCase.execute()
            currentWeatherResult
                .onSuccess { _currentWeather.value = Result.success(it) }
                .onFailure { _currentWeather.value = Result.failure(it) }
        }
    }
}