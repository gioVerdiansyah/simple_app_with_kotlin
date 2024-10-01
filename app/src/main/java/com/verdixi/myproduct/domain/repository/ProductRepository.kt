package com.verdixi.myproduct.domain.repository

import com.verdixi.myproduct.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(googleDriveLink: String): List<Product>
}