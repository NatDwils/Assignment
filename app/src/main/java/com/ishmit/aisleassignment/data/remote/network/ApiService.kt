package com.ishmit.aisleassignment.data.remote.network

import com.ishmit.aisleassignment.data.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("users/phone_number_login")
    suspend fun phoneNumberLogin(@Body request: PhoneNumberRequest): Response<PhoneNumberResponse>

    @POST("users/verify_otp")
    suspend fun verifyOtp(@Body request: OtpRequest): Response<OtpResponse>

    @GET("users/test_profile_list")
    suspend fun getNotes(@Header("Authorization") token: String): Response<NotesResponse>
}