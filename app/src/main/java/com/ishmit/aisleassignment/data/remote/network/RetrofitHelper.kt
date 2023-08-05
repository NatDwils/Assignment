package com.ishmit.aisleassignment.data.remote.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper {

    // Base URL of the API
    private const val BASE_URL = "https://app.aisle.co/V1/"

    // Function to create a Retrofit instance
    private fun getRetrofit(): Retrofit {
        // Create a Moshi instance with KotlinJsonAdapterFactory
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        // Create and configure a Retrofit instance
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // Function to create an instance of ApiService using Retrofit
    fun create(): ApiService {
        // Create a Retrofit instance and return an ApiService interface implementation
        return getRetrofit().create(ApiService::class.java)
    }
}
