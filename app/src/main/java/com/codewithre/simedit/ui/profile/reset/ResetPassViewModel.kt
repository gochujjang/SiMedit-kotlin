package com.codewithre.simedit.ui.profile.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.ResetPassResponse
import kotlinx.coroutines.launch

class ResetPassViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<ResetPassResponse?>()
    val message: LiveData<ResetPassResponse?> = _message

    fun resetPass(
        currentPass: String,
        password: String,
        passConfirm: String,
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.resetPass(currentPass, password, passConfirm)
                _message.value = response
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}

