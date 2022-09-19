package com.JJ.weathercodechallenge.utils

sealed class Result<out T, out E> {

    abstract val isLoading: Boolean
    abstract val isSuccess: Boolean
    abstract val isFailure: Boolean

    abstract fun getOrNull(): T?
    abstract fun errorOrNull(): E?

    companion object {
        fun <T, E> loading(): Result<T, E> =
            Loading()
        fun <T, E> success(value: T): Result<T, E> =
            Success(value)
        fun <T, E> failure(error: E): Result<T, E> =
            Failure(error)
    }

    private class Loading<T, E> : Result<T, E>() {
        override val isLoading: Boolean get() = true
        override val isSuccess: Boolean get() = false
        override val isFailure: Boolean get() = false

        override fun getOrNull(): T? = null
        override fun errorOrNull(): E? = null
    }

    private class Success<T, E>(private val value: T) : Result<T, E>() {
        override fun equals(other: Any?): Boolean =
            this === other || other is Success<*, *> && this.value == other.value

        override fun hashCode(): Int = 7 + 31 * value.hashCode()

        override fun toString(): String = "Success($value)"

        override val isLoading: Boolean get() = false
        override val isSuccess: Boolean get() = true
        override val isFailure: Boolean get() = false

        override fun getOrNull(): T? = value
        override fun errorOrNull(): E? = null
    }

    private class Failure<T, E>(private val error: E) : Result<T, E>() {
        override fun equals(other: Any?): Boolean =
            this === other || other is Failure<*, *> && this.error == other.error

        override fun hashCode(): Int = 11 + 31 * error.hashCode()

        override fun toString(): String = "Failure($error)"

        override val isLoading: Boolean get() = false
        override val isSuccess: Boolean get() = false
        override val isFailure: Boolean get() = true

        override fun getOrNull(): T? = null
        override fun errorOrNull(): E? = error
    }
}

inline fun <T, E> Result<T, E>.onLoading(action: (value: T) -> Unit): Result<T, E> {
    @Suppress("UNCHECKED_CAST")
    if (isLoading) action(getOrNull() as T)
    return this
}

inline fun <T, E> Result<T, E>.onSuccess(action: (value: T) -> Unit): Result<T, E> {
    @Suppress("UNCHECKED_CAST")
    if (isSuccess) action(getOrNull() as T)
    return this
}

inline fun <T, E> Result<T, E>.onFailure(action: (error: E) -> Unit): Result<T, E> {
    @Suppress("UNCHECKED_CAST")
    if (isFailure) action(errorOrNull() as E)
    return this
}