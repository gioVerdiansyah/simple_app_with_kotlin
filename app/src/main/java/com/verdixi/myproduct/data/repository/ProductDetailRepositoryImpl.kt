package com.verdixi.myproduct.data.repository

import com.verdixi.myproduct.data.api.ApiService
import com.verdixi.myproduct.data.model.ProductDetailResponse
import com.verdixi.myproduct.domain.model.ProductDetail
import com.verdixi.myproduct.domain.repository.ProductDetailRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class ProductDetailRepositoryImpl(private val apiService: ApiService) : ProductDetailRepository {
    override suspend fun getDetails(googleDriveLink: String): ProductDetail {
        val res: ProductDetailResponse = apiService.getProductDetails(googleDriveLink)

        return ProductDetail(
            material = res.material,
            manufacturer = res.manufacturer,
            warranty = res.warranty,
            date = LocalDate.parse(
                res.date,
                DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale("id", "ID"))
            ),
            stock = res.stock,
            sold = res.sold,
            rating = res.rating,
            category = res.category.joinToString(separator = " ") { "#$it" }
        )
    }
}