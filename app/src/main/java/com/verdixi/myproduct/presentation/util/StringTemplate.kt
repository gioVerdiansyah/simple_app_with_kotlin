package com.verdixi.myproduct.presentation.util
import java.text.NumberFormat
import java.util.Locale

fun formatRupiah(price: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    return format.format(price)
}

fun limitDescription(description: String, wordLimit: Int): String {
    val words = description.split(" ")
    return if (words.size > wordLimit) {
        words.take(wordLimit).joinToString(" ") + "..."
    } else {
        description
    }
}