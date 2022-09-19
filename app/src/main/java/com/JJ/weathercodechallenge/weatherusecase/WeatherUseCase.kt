package com.JJ.weathercodechallenge.weatherusecase

import com.JJ.weathercodechallenge.model.WeatherResponse
import com.JJ.weathercodechallenge.model.errorResponse.ErrorResponse
import com.JJ.weathercodechallenge.model.errorResponse.ErrorResponseFailure
import com.JJ.weathercodechallenge.model.errorResponse.ResponseErrorType
import com.JJ.weathercodechallenge.network.Rest
import com.JJ.weathercodechallenge.utils.*

typealias WeatherUseCase = ResultUseCase<WeatherResponse>

class WeatherUseCaseImpl(
    private val city: String
) : WeatherUseCase {

    override suspend fun execute(): UseCaseResult<WeatherResponse> {
        return handleRetrofitResponse(
            { Rest.retrofitClient.retrieveCityWeather(city) },
            {
                it.body()?.let { body ->
                    Result.success(body)
                } ?: Result.failure(GenericServiceFailure)
            }
        ) { response ->
            ErrorResponseFailure(
                ErrorResponse(
                    ResponseErrorType.SERVICE_ERROR,
                    response.message(),
                    response.code(),
                    response.errorBody()?.string()
                )
            )

        }
    }
}