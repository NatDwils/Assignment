package com.ishmit.aisleassignment.data.remote.repository

import com.ishmit.aisleassignment.data.remote.network.ApiService
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import com.ishmit.aisleassignment.data.models.*
import com.ishmit.aisleassignment.utils.apiCall.ApiCall


class UserRepository(private val apiService: ApiService) {

    suspend fun phoneNumberLogin(number: String): ResponseRequest<PhoneNumberResponse> {
        val request = PhoneNumberRequest(number)
        return ApiCall.apiCall {
            apiService.phoneNumberLogin(request)
        }
    }

    suspend fun verifyOtp(number: String, otp: String): ResponseRequest<OtpResponse> {
        val request = OtpRequest(number, otp)
        return ApiCall.apiCall {
            apiService.verifyOtp(request)
        }
    }

    suspend fun getNotes(authToken: String): ResponseRequest<NotesResponse> {
        return ApiCall.apiCall {
            apiService.getNotes(authToken)
        }
    }

}