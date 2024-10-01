package com.verdixi.myproduct.data.api

import com.verdixi.myproduct.data.model.ProductDetailResponse
import com.verdixi.myproduct.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
//    @GET("product")
    @GET
    suspend fun getProducts(@Url fileUrl: String): List<ProductResponse>

    @GET
    suspend fun getProductDetails(@Url fileUrl: String): ProductDetailResponse
}