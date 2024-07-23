package com.codewithre.simedit.ui.add.transacsaving

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.AddTransacResponse
import com.codewithre.simedit.data.remote.response.AddTransacSavingResponse
import com.codewithre.simedit.data.remote.response.DropdownItem
import com.codewithre.simedit.data.remote.response.DropdownSavingResponse
import kotlinx.coroutines.launch

class AddTransacSavingViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _addTransactionResult = MutableLiveData<AddTransacSavingResponse>()
    val addTransactionResult: LiveData<AddTransacSavingResponse> = _addTransactionResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun addSavingTransaction(
        status : String,
        nominal : Int,
        portomember_id : Int,
        keterangan : String
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.addTransactionSaving(
                    status,
                    nominal,
                    portomember_id,
                    keterangan
                )
                if (response.success == true) {
                    _addTransactionResult.value = response
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

//    private val _dropdownSaving = MutableLiveData<List<DropdownItem?>?>()
//    val dropdownSaving: LiveData<List<DropdownItem?>?> = _dropdownSaving
//
//    fun getDropdownSaving() {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = repository.getDropdownSaving()
//                if (response.success == true) {
//                    _dropdownSaving.value = response.data
//                } else {
//                    _errorMessage.value = response.message ?: "An error occurred"
//                }
//            } catch (e: Exception) {
//                _errorMessage.value = e.message ?: "An error occurred"
//                e.printStackTrace()
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }