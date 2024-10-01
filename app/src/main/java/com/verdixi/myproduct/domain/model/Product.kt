package com.verdixi.myproduct.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Mature data
@Parcelize
data class Product (
    val thumbnail: String,
    val name: String,
    val price: Long,
    val description: String,
    val detailsLink: String
): Parcelable