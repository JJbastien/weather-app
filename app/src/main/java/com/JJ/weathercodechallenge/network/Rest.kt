package com.JJ.weathercodechallenge.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object Rest {

    private val defaultMoshi = Moshi.Builder()

    val retrofitClient: WeatherApi
    get() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    defaultMoshi.addLast(KotlinJsonAdapterFactory()).build()
                )
            )
            .client(okHttpClient)
            .baseUrl(WeatherApi.BASE_URL)
            .build()
            .create(WeatherApi::class.java)

    }
}