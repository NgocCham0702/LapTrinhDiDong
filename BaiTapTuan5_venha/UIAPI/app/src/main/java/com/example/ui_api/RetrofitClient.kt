package com.example.ui_api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient
{
    fun apiService (): ApiInterface {
        return Retrofit.Builder().baseUrl("https://amock.io/api/researchUTH/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiInterface::class.java)
    }
}