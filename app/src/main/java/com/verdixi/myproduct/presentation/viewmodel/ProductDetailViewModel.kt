package com.verdixi.myproduct.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verdixi.myproduct.domain.model.ProductDetail
import com.verdixi.myproduct.domain.usecase.GetProductDetailUseCase
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val getProductDetailUseCase: GetProductDetailUseCase) : ViewModel() {
    private val _productDetails = MutableLiveData<ProductDetail>()
    val products: LiveData<ProductDetail> get() = _productDetails

    fun fetchProductDetails(googleDriveLink: String) {
        viewModelScope.launch {
            try {
                val result = getProductDetailUseCase.execute(googleDriveLink)
                _productDetails.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}