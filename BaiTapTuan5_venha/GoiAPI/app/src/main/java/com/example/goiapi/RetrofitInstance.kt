package com.example.goiapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitInstance {
    val api: ProductApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://mock.apidog.com/m1/890655-872447-default/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }
}