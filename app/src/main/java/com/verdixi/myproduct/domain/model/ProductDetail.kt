package com.verdixi.myproduct.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

// Mature data
@Parcelize
data class ProductDetail(
    val material: String,
    val manufacturer: String,
    val warranty: String,
    val date: LocalDate,
    val stock: Long,
    val sold: Long,
    val rating: Double,
    val category: String
) : Parcelable
