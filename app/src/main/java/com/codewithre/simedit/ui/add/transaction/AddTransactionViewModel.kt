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
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}