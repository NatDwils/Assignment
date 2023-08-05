package com.ishmit.aisleassignment.utils.apiCall

import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import retrofit2.Response

object ApiCall {
    suspend fun <T : Any> apiCall(apiCall: suspend () -> Response<T>): ResponseRequest<T> {
        try {
            val response = apiCall()
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ResponseRequest.Success(body)
                } else {
                    ResponseRequest.Failure("Response body is null.")
                }
            } else {
                ResponseRequest.Failure(response.errorBody()?.string())
            }
        } catch (e: Exception) {
            return ResponseRequest.Failure(e.message)
        }
    }
}