package com.ngiritin.app.model

// Simpan di file TransactionModel.kt

data class Transaction(
    val title: String,
    val category: String,
    val amount: String,
    val note: String,
    val time: String
)

// Ini trik "pro"-nya: Sealed class buat list campuran
sealed class HistoryItem {
    data class Header(val date: String) : HistoryItem()
    data class Content(val transaction: Transaction) : HistoryItem()
}