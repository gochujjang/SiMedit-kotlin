package com.codewithre.simedit.ui.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.UpdateProfileResponse
import com.codewithre.simedit.data.remote.response.UserResponse
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _updateProfileResult = MutableLiveData<UpdateProfileResponse>()
    val updateProfileResult: LiveData<UpdateProfileResponse> = _updateProfileResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _userData = MutableLiveData<UserResponse>()
    val userData: LiveData<UserResponse> = _userData

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

    fun updateProfile(
        name: String,
        username: String,
        email: String,
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.updateProfile(name, username, email)
                if (response.success == true) {
                    _updateProfileResult.value = response
                    _errorMessage.value = response.message
                } else {
                    _errorMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An error occurred"
                e.printStackTrace()
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}