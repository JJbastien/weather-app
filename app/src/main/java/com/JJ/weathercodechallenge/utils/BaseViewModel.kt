package com.JJ.weathercodechallenge.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.JJ.weathercodechallenge.BuildConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import java.lang.RuntimeException

abstract class BaseViewModel: ViewModel() {

    protected open val viewModelScopeSafe by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    open val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            if (throwable !is CancellationException) {
                if (BuildConfig.DEBUG) {
                    // In Debug mode, crash the app
                    throw RuntimeException("Unhandled Coroutine Exception!", throwable)
                } else {
                    Log.e("ViewModel", "Unhandled Coroutine Exception!", throwable)
                }
            }
        }
    }
}