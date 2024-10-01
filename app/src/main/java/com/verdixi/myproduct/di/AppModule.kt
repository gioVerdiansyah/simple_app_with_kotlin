package com.verdixi.myproduct.di

import com.verdixi.myproduct.data.api.ApiClient
import com.verdixi.myproduct.data.repository.ProductDetailRepositoryImpl
import com.verdixi.myproduct.data.repository.ProductRepositoryImpl
import com.verdixi.myproduct.domain.repository.ProductDetailRepository
import com.verdixi.myproduct.domain.repository.ProductRepository
import com.verdixi.myproduct.domain.usecase.GetProductDetailUseCase
import com.verdixi.myproduct.domain.usecase.GetProductUseCase
import com.verdixi.myproduct.presentation.viewmodel.ProductDetailViewModel
import com.verdixi.myproduct.presentation.viewmodel.ProductViewModel

object AppModule {
    private val apiService = ApiClient.apiService

    //    Product
    private val productRepository: ProductRepository = ProductRepositoryImpl(apiService)
    private val getProduct = GetProductUseCase(productRepository)

    fun provideProductViewModel(): ProductViewModel {
        return ProductViewModel(getProduct)
    }

    //    Product Detail
    private val productDetailRepository: ProductDetailRepository = ProductDetailRepositoryImpl(apiService)
    private val getProductDetail = GetProductDetailUseCase(productDetailRepository)

    fun provideProductDetailViewModel(): ProductDetailViewModel {
        return ProductDetailViewModel(getProductDetail)
    }
}