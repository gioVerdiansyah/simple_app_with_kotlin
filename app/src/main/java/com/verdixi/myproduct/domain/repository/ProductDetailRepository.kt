package com.verdixi.myproduct.domain.repository

import com.verdixi.myproduct.domain.model.ProductDetail

interface ProductDetailRepository {
    suspend fun getDetails(googleDriveLink: String): ProductDetail
}