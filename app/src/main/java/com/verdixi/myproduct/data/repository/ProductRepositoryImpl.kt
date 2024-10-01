package com.verdixi.myproduct.data.repository

import com.verdixi.myproduct.data.api.ApiService
import com.verdixi.myproduct.data.model.ProductResponse
import com.verdixi.myproduct.domain.model.Product
import com.verdixi.myproduct.domain.repository.ProductRepository

class ProductRepositoryImpl(private val apiService: ApiService) : ProductRepository {
    override suspend fun getProducts(googleDriveLink: String): List<Product> {
        val productRes: List<ProductResponse> = apiService.getProducts(googleDriveLink)
        return productRes.map { data ->
            Product(
                thumbnail = data.thumbnail,
                name = data.name,
                price = data.price,
                description = data.description,
                detailsLink = data.details_link,
            )
        }
    }
}