package com.JJ.weathercodechallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.JJ.weathercodechallenge.R
import com.JJ.weathercodechallenge.model.Forecast
import com.JJ.weathercodechallenge.utils.displayTemp

class ForecastAdapter(
    private val context: Context,
    private val detailsListener: ForecastDetailsListener,
    private val forecastList: HashSet<Forecast> = hashSetOf()
) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    fun setNewData(forecastData: List<Forecast>) {
        forecastList.clear()
        forecastList.addAll(forecastData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val forecastView = LayoutInflater.from(context).inflate(R.layout.days_forecast, parent, false)
        return ForecastViewHolder(forecastView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = forecastList.toList()[position]
        holder.weather.text = forecast.weather[0].main
        holder.temperature.text = displayTemp(forecast.main.temp)

        holder.itemView.setOnClickListener {
            detailsListener.navigateToDetails(forecast)
        }
    }

    override fun getItemCount(): Int = forecastList.size

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weather: TextView = itemView.findViewById(R.id.weather_state)
        val temperature: TextView = itemView.findViewById(R.id.weather_temp)
    }
}

interface ForecastDetailsListener {
    fun navigateToDetails(forecast: Forecast)
}