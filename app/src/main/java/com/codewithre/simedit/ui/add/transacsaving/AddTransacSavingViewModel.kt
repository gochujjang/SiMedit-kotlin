package com.codewithre.simedit.ui.add.transacsaving

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.AddTransacResponse
import com.codewithre.simedit.data.remote.response.AddTransacSavingResponse
import com.codewithre.simedit.data.remote.response.DropdownItem
import com.codewithre.simedit.data.remote.response.DropdownSavingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.math.BigInteger

class AddTransacSavingViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _addTransactionResult = MutableLiveData<AddTransacSavingResponse>()
    val addTransactionResult: LiveData<AddTransacSavingResponse> = _addTransactionResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _transactionUploadStatus = MutableLiveData<Boolean>()
    val transactionUploadStatus: LiveData<Boolean> = _transactionUploadStatus

    fun addSavingTransaction(
        status: RequestBody,
        nominal: RequestBody,
        portomember_id: RequestBody,
        keterangan: RequestBody,
        description: RequestBody,
        photo: MultipartBody.Part
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.addTransactionSaving(
                        status,
                        nominal,
                        portomember_id,
                        keterangan,
                        description,
                        photo
                    )
                }
                withContext(Dispatchers.Main) {
                    if (response.success == true) {
                        _addTransactionResult.value = response
                        _transactionUploadStatus.value = true // Mark success
                    } else {
                        _errorMessage.value = response.message ?: "An error occurred"
                        _transactionUploadStatus.value = false // Mark failure
                    }
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message ?: "An error occurred"
                    _transactionUploadStatus.value = false // Mark failure
                    _isLoading.value = false
                }
                e.printStackTrace()
            }
        }
    }
}