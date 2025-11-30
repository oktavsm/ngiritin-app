package com.ngiritin.app.data.model

data class Transaction(
    val title: String,
    val category: String,
    val amount: String,
    val note: String,
    val time: String
)

sealed class HistoryItem {
    data class Header(val date: String) : HistoryItem()
    data class Content(val transaction: Transaction) : HistoryItem()
}