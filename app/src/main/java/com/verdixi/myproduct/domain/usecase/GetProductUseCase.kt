package com.verdixi.myproduct.domain.usecase

import com.verdixi.myproduct.domain.model.Product
import com.verdixi.myproduct.domain.repository.ProductRepository

class GetProductUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(googleDriveLink: String): List<Product> {
        return productRepository.getProducts(googleDriveLink)
    }
}