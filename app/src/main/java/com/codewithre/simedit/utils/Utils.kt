package com.codewithre.simedit.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(createdAt: String): String {
//    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    val date = inputFormat.parse(createdAt)
    return outputFormat.format(date?:"")
}

fun formatCurrency(amount: Int?): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    numberFormat.maximumFractionDigits = 0
    numberFormat.minimumFractionDigits = 0
    val formattedNominal = numberFormat.format(amount)
    return formattedNominal.replace("Rp", "Rp ")
}

fun formatDateApi(dateString: String?): String {
    return if (dateString != null) {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            if (date != null) {
                outputFormat.format(date)
            } else {
                "Unknown date"
            }
        } catch (e: Exception) {
            "Invalid date format"
        }
    } else {
        "No date available"
    }
}