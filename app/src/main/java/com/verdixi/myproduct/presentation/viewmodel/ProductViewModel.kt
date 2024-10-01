package com.verdixi.myproduct.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verdixi.myproduct.domain.model.Product
import com.verdixi.myproduct.domain.usecase.GetProductUseCase
import kotlinx.coroutines.launch

class ProductViewModel(private val getProductUseCase: GetProductUseCase): ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun fetchProducts(googleDriveId: String) {
        viewModelScope.launch {
            try {
                val result = getProductUseCase.execute("https://drive.google.com/uc?id=$googleDriveId")
                _products.postValue(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}