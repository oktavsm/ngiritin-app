package com.ngiritin.app.ui.new_transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ngiritin.app.utils.Result

class TransactionViewModel : ViewModel() {
    private val _aiInputText = MutableLiveData<String>()
    val aiInputText: LiveData<String> = _aiInputText
    private val _manualAmount = MutableLiveData<Double>()
    private val _transactionResult = MutableLiveData<Result<Boolean>>()
    val transactionResult: LiveData<Result<Boolean>> = _transactionResult

    fun analyzeText(text: String) {
        _aiInputText.value = text
    }

    fun saveManualTransaction(amount: Double, category: String, type: String) {

    }
}