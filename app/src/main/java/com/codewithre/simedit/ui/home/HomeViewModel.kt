package com.codewithre.simedit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.BalanceResponse
import com.codewithre.simedit.data.remote.response.UserResponse
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalBalance = MutableLiveData<BalanceResponse?>()
    val totalBalance: LiveData<BalanceResponse?> = _totalBalance

    private val _userData = MutableLiveData<UserResponse>()
    val userData: LiveData<UserResponse> = _userData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun getUserData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getUser()
                _userData.value = response
            } catch (e: Exception) {
                e.printStackTrace()
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
}