package com.verdixi.myproduct.data.model

data class ProductDetailResponse (
    val material: String,
    val manufacturer: String,
    val warranty: String,
    val date: String,
    val stock: Long,
    val sold: Long,
    val rating: Double,
    val category: List<String>
)