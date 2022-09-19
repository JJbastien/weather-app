package com.JJ.weathercodechallenge.utils

import com.JJ.weathercodechallenge.model.errorResponse.ErrorResponse
import com.JJ.weathercodechallenge.model.errorResponse.ResponseErrorType

open class Failure(val errorData: ErrorResponse? = null)

open class NetworkConnectionFailure(private val error: ErrorResponse? = null) : Failure(error)

object GenericNetworkConnectionFailure : NetworkConnectionFailure(null)

open class ServiceFailure(private val error: ErrorResponse? = null) : Failure(error)

object GenericServiceFailure : ServiceFailure(null)

open class ExceptionFailure(private val e: Throwable) : Failure(
    ErrorResponse(
        ResponseErrorType.EXCEPTION_ERROR,
        e.localizedMessage,
        0,
        e.stackTraceToString()
    )
)