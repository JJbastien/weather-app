package com.JJ.weathercodechallenge.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.JJ.weathercodechallenge.databinding.FragmentWeatherDetailsBinding
import com.JJ.weathercodechallenge.model.Forecast
import com.JJ.weathercodechallenge.utils.*
import com.JJ.weathercodechallenge.viewmodel.WeatherViewModel

class WeatherDetailsFragment : Fragment() {

    private lateinit var binding: FragmentWeatherDetailsBinding

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDetailsBinding.inflate(layoutInflater)
        viewModel.forecastDetails.observe(viewLifecycleOwner, ::handleForecastDetails)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.weatherToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun handleForecastDetails(result: Result<Forecast, Failure>) {
        result
            .onSuccess { setForecastDetails(it) }
    }

    private fun setForecastDetails(forecast: Forecast) {
        binding.forecastTemp.text = kelvinToFahrenheit(forecast.main.temp)
        binding.forecastFeelsLike.text = feelLikeTemp(forecast.main.feelsLike)
        binding.forecastWeather.text = forecast.weather[0].main
        binding.forecastDescription.text = forecast.weather[0].description
    }

    companion object {
        fun newInstance() = WeatherDetailsFragment()
    }
}