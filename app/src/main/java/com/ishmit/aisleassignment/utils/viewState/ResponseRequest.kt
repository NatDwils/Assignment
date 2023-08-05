package com.ishmit.aisleassignment.utils.viewState

sealed class ResponseRequest<out T> {
    object Loading : ResponseRequest<Nothing>()
    data class Success<out T>(val data: T) : ResponseRequest<T>()
    data class Failure(val error: String?) : ResponseRequest<Nothing>()
}
