package com.ishmit.aisleassignment.utils.apiCall

import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import retrofit2.Response

object ApiCall {
    // Function to perform API call and handle response states
    suspend fun <T : Any> apiCall(apiCall: suspend () -> Response<T>): ResponseRequest<T> {
        try {
            // Execute the provided API call
            val response = apiCall()

            // Check if the response is successful
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    // Return Success state with the response body
                    ResponseRequest.Success(body)
                } else {
                    // Return Failure state with an error message
                    ResponseRequest.Failure("Response body is null.")
                }
            } else {
                // Return Failure state with the error response body
                ResponseRequest.Failure(response.errorBody()?.string())
            }
        } catch (e: Exception) {
            // Return Failure state with the exception message
            return ResponseRequest.Failure(e.message)
        }
    }
}
