package com.codewithre.simedit.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.AuthRepository
import com.codewithre.simedit.data.remote.response.RegisterData
import com.codewithre.simedit.data.remote.response.RegisterResponse
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _errorResponse = MutableLiveData<String?>()
    val errorResponse: LiveData<String?> = _errorResponse

    fun register(
        name: String,
        username: String,
        email: String,
        password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.register(name, username, email, password)
//                _registerResult.value = response
//                _errorResponse.value = null
                if (response.data != null) {
                    _registerResult.value = response
                    _errorResponse.value = null
                } else {
                    handleErrorResponse(response)
                    Log.d("COY RegisterViewModel", "Error response: $response")
                    Log.d("COY RegisterViewModel", "Error message response: ${response.email}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun handleErrorResponse(message: RegisterResponse?) {
        if (message != null) {
            val emailErrors = message.email?.joinToString(", ") ?: ""
            val usernameErrors = message.username?.joinToString(", ") ?: ""
            if (emailErrors.isEmpty()) {
                _errorResponse.value = usernameErrors
            } else {
                _errorResponse.value = emailErrors
            }
//            _errorResponse.value = "Email: $emailErrors\nUsername: $usernameErrors"
            Log.d("COY RegisterViewModel", "Error response: $message")
        } else {
            _errorResponse.value = "Unknown error occurred"
        }
    }
}