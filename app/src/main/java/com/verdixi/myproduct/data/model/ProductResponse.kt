package com.verdixi.myproduct.data.model

// Row data
data class ProductResponse (
    val thumbnail: String,
    val name: String,
    val price: Long,
    val description: String,
    val details_link: String
)