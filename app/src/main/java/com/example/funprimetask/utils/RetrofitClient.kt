package com.example.funprimetask.utils

import com.example.funprimetask.interfaces.PhotoApiServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun create(): PhotoApiServiceInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(PhotoApiServiceInterface::class.java)
    }
}