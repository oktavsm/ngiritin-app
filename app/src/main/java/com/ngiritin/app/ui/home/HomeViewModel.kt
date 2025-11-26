package com.ngiritin.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar

class HomeViewModel : ViewModel() {

    private val _isBalanceVisible = MutableLiveData<Boolean>()
    val isBalanceVisible: LiveData<Boolean> = _isBalanceVisible

    private val _headerData = MutableLiveData<HeaderData>()
    val headerData: LiveData<HeaderData> = _headerData

    private val _transactionList = MutableLiveData<List<DummyTransaction>>()
    val transactionList: LiveData<List<DummyTransaction>> = _transactionList

    init {
        _isBalanceVisible.value = false
        loadDummyData()
    }

    fun toggleBalanceVisibility() {
        _isBalanceVisible.value = !(_isBalanceVisible.value ?: false)
    }

    private fun loadDummyData() {
        _headerData.value = HeaderData(
            greeting = getGreetingMessage(),
            username = "Arlott",
            balance = 2540000.0,
            income = 5000000.0,
            expense = 2550000.0,
            insightCity = "Malang",
            insightMin = 2600000.0,
            insightMax = 3200000.0
        )

        _transactionList.value = listOf(
            DummyTransaction("Coffee at Starbucks", "Food", 120000.0, "Morning coffee", "13.00 AM"),
            DummyTransaction("Indomaret Snacks", "Food", 25000.0, "Beli chiki", "10.30 AM"),
            DummyTransaction("Gojek to Campus", "Transport", 15000.0, "Ojek ke kampus", "07.45 AM")
        )
    }

    private fun getGreetingMessage(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            in 16..20 -> "Good Evening"
            else -> "Good Night"
        }
    }

    data class HeaderData(
        val greeting: String,
        val username: String,
        val balance: Double,
        val income: Double,
        val expense: Double,
        val insightCity: String,
        val insightMin: Double,
        val insightMax: Double
    )

    data class DummyTransaction(
        val title: String,
        val category: String,
        val amount: Double,
        val note: String,
        val time: String
    )
}