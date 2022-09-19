package com.JJ.weathercodechallenge.utils

import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus

abstract class BaseFragment : Fragment() {

    protected val lifecycleScopeSafe by lazy { lifecycleScope + coroutineExceptionHandler }

    open val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            if (throwable !is CancellationException) {
                Log.e("Fragment", "Unhandled Coroutine Exception!", throwable)
            }
        }
    }

    protected open fun setTitle(@StringRes title: Int) {
        setTitle(getString(title))
    }

    protected open fun setTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }


}