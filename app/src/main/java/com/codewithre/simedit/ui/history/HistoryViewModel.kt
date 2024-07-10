package com.codewithre.simedit.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.BalanceResponse
import com.codewithre.simedit.data.remote.response.HistoryItem
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listHistory = MutableLiveData<List<HistoryItem?>?>()
    val listHistory: LiveData<List<HistoryItem?>?> = _listHistory

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _totalBalance = MutableLiveData<BalanceResponse?>()
    val totalBalance: LiveData<BalanceResponse?> = _totalBalance

    private val _totalIncome = MutableLiveData<BalanceResponse?>()
    val totalIncome: LiveData<BalanceResponse?> = _totalIncome

    private val _totalExpense = MutableLiveData<BalanceResponse?>()
    val totalExpense: LiveData<BalanceResponse?> = _totalExpense

    fun getTotalIncome() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getTotalIncome()
                if (response.success == true) {
                    _totalIncome.value = response
                } else {
                    _errorMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getTotalExpense() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getTotalExpense()
                if (response.success == true) {
                    _totalExpense.value = response
                } else {
                    _errorMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getTotalBalance() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getTotalBalance()
                if (response.success == true) {
                    _totalBalance.value = response
                } else {
                    _errorMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getHistory() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getHistory()
                if (response.success == true) {
                    _listHistory.value = response.data
                } else {
                    _errorMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}