package com.codewithre.simedit.ui.savings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.BalanceResponse
import com.codewithre.simedit.data.remote.response.SavingItem
import com.codewithre.simedit.data.remote.response.TotalTargetResponse
import com.codewithre.simedit.data.remote.response.TotalTerkumpulResponse
import kotlinx.coroutines.launch

class SavingsViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listSaving = MutableLiveData<List<SavingItem?>?>()
    val listSaving: LiveData<List<SavingItem?>?> = _listSaving

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _totalSaving = MutableLiveData<TotalTerkumpulResponse?>()
    val totalSaving: LiveData<TotalTerkumpulResponse?> = _totalSaving

    private val _totalSaveTarget = MutableLiveData<TotalTargetResponse?>()
    val totalSaveTarget: LiveData<TotalTargetResponse?> = _totalSaveTarget

    fun getSaving() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getSaving()
                val totalSaving = repository.getTotalSave()
                val totalSavingTarget = repository.getTotalSaveTarget()
                if (response.success == true) {
                    _listSaving.value = response.data
                    _totalSaving.value = totalSaving
                    _totalSaveTarget.value = totalSavingTarget
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