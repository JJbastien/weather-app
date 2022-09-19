package com.JJ.weathercodechallenge.utils

import com.JJ.weathercodechallenge.model.errorResponse.ErrorResponse
import com.JJ.weathercodechallenge.model.errorResponse.ResponseErrorType
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.TimeoutException

inline fun <In, Out> handleRetrofitResponse(
    request: () -> Response<In>,
    onSuccess: (Response<In>) -> Result<Out, Failure>,
    onFailure: (Response<In>) -> Failure
): Result<Out, Failure> {
    return try {
        val response = request()
        when {
            response.isSuccessful -> onSuccess(response)
            else -> Result.failure(onFailure(response))
        }
    } catch (throwable: Throwable) {
        Result.failure(
            when (throwable) {
                is HttpException, is TimeoutException -> NetworkConnectionFailure(
                    ErrorResponse(
                        ResponseErrorType.NETWORK_CONNECTION,
                        throwable.localizedMessage,
                        errorBody = throwable.stackTraceToString()
                    )
                )
                else -> ExceptionFailure(throwable)
            }
        )
    }
}