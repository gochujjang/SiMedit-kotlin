package com.codewithre.simedit.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.AuthRepository
import com.codewithre.simedit.data.database.models.User
import com.codewithre.simedit.data.remote.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private fun saveSession(user: User) {
        viewModelScope.launch {
                repository.saveSession(user)
        }
    }

    fun login(username: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                saveSession(User(username, response.accessToken))
                _loginResult.value = Result.success(response)
                Log.d("COY LOGIN","loginResult : " + _loginResult.value)
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}