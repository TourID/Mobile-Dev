package com.bangkit2024.tourid.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    fun getProducts(): List<ProductsItem>

    @GET("search/products")
    fun getResultProducts(
        @Query("q") q: String
    ): DummyResponse
}