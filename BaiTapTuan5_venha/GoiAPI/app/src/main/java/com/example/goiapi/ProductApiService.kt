package com.example.goiapi

import retrofit2.http.GET

interface ProductApiService {
    @GET("v2/product")
    suspend fun getProduct(): Product
}
