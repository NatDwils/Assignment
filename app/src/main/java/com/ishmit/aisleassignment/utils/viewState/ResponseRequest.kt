package com.ishmit.aisleassignment.utils.viewState

sealed class ResponseRequest<out T> {
    // Represents the loading state
    object Loading : ResponseRequest<Nothing>()

    // Represents the success state with associated data
    data class Success<out T>(val data: T) : ResponseRequest<T>()

    // Represents the failure state with an associated error message
    data class Failure(val error: String?) : ResponseRequest<Nothing>()
}
