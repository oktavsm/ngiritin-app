package com.ngiritin.app.utils

object CurrencyHelper {
    fun formatRupiah(number: Double): String {
        return String.format("%,.0f", number)
    }

    fun formatCompact(number: Double): String {
        return if (number >= 1_000_000) {
            String.format("%.1fM", number / 1_000_000)
        } else if (number >= 1_000) {
            String.format("%.1fK", number / 1_000)
        } else {
            formatRupiah(number)
        }
    }
}