package com.codewithre.simedit.ui.add.saving

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.AddSavingResponse
import com.codewithre.simedit.data.remote.response.AddTransacResponse
import kotlinx.coroutines.launch

class AddSavingViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _addSavingResult = MutableLiveData<AddSavingResponse>()
    val addSavingResult: LiveData<AddSavingResponse> = _addSavingResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun addSaving(
        title : String,
        target : Int,
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.addSaving(title, target)
                if (response.success == true) {
                    _addSavingResult.value = response
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