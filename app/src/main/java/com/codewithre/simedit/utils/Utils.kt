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

fun formatShortCurrency(amount: Int?): String {
    if (amount == null) return ""

    val suffixes = listOf("", "jt", "M", "B", "T")
    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    formatter.minimumFractionDigits = 0
    formatter.maximumFractionDigits = 2

    val divisor = 100000000
    var index = 0
    var amount = amount.toDouble()

    while (amount >= divisor && index < suffixes.size - 1) {
        amount /= divisor
        amount *= 100
        index++
    }

    val formatted = formatter.format(amount)
    return formatted.replace("Rp", "Rp ").replace(",00", "") + suffixes[index]
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