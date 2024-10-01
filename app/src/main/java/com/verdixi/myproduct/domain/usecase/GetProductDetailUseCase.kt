package com.verdixi.myproduct.domain.usecase

import com.verdixi.myproduct.domain.model.ProductDetail
import com.verdixi.myproduct.domain.repository.ProductDetailRepository

class GetProductDetailUseCase(private val productDetailRepository: ProductDetailRepository) {
    suspend fun execute (googleDriveLink: String): ProductDetail{
        return productDetailRepository.getDetails(googleDriveLink)
    }
}