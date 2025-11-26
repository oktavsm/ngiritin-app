package com.ngiritin.app.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngiritin.app.model.HistoryItem
import com.ngiritin.app.model.Transaction

class HistoryViewModel : ViewModel() {

    private val _transactionList = MutableLiveData<List<HistoryItem>>()
    val transactionList: LiveData<List<HistoryItem>> = _transactionList

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        val items = mutableListOf<HistoryItem>()

        // TODAY
        items.add(HistoryItem.Header("Today"))
        items.add(HistoryItem.Content(
            Transaction(
                "Coffee at Starbucks",
                "Food",
                "IDR 120,000",
                "Note : Morning coffee before class",
                "13.00 AM"
            )
        ))
        items.add(HistoryItem.Content(Transaction("Coffee at Starbucks", "Food", "IDR 120,000", "Note : Morning coffee before class", "13.00 AM")))

        // YESTERDAY
        items.add(HistoryItem.Header("Yesterday"))
        items.add(HistoryItem.Content(Transaction("Coffee at Starbucks", "Food", "IDR 120,000", "Note : Morning coffee before class", "13.00 AM")))

        // DATE
        items.add(HistoryItem.Header("October 25, 2025"))
        items.add(HistoryItem.Content(Transaction("Coffee at Starbucks", "Food", "IDR 120,000", "Note : Morning coffee before class", "13.00 AM")))

        _transactionList.value = items
    }
}