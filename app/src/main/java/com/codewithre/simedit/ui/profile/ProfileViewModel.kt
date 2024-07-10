package com.codewithre.simedit.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.UserResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userData = MutableLiveData<UserResponse>()
    val userData: LiveData<UserResponse> = _userData
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

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
}