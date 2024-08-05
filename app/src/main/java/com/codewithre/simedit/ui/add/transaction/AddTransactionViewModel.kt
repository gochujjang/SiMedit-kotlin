package com.codewithre.simedit.ui.add.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.AddTransacResponse
import kotlinx.coroutines.launch

class AddTransactionViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _addTransactionResult = MutableLiveData<AddTransacResponse>()
    val addTransactionResult: LiveData<AddTransacResponse> = _addTransactionResult

    private val _transactionUploadStatus = MutableLiveData<Boolean>()
    val transactionUploadStatus: LiveData<Boolean> = _transactionUploadStatus

    private val _transactionChangedEvent = MutableLiveData<Unit>()
    val transactionChangedEvent: LiveData<Unit> = _transactionChangedEvent

    fun notifyTransactionChanged() {
        _transactionChangedEvent.value = Unit
    }

    fun addTransaction(
        status : String,
        nominal : String,
        tgl : String,
        keterangan : String
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.addTransaction(
                    status,
                    nominal,
                    tgl,
                    keterangan)
                _addTransactionResult.value = response
                _transactionChangedEvent.value = Unit // Notify that a transaction was added
                _transactionUploadStatus.value = true // Mark success
            } catch (e: Exception) {
                e.printStackTrace()
                _transactionUploadStatus.value = false // Mark success
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}