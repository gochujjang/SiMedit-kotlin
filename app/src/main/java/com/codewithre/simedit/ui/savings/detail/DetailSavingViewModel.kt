package com.codewithre.simedit.ui.savings.detail

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.DeleteResponse
import com.codewithre.simedit.data.remote.response.DetailSavingResponse
import com.codewithre.simedit.data.remote.response.MemberItem
import com.codewithre.simedit.data.remote.response.SavingDetailItem
import com.codewithre.simedit.data.remote.response.TransaksiPortoItem
import kotlinx.coroutines.launch
import java.math.BigInteger

class DetailSavingViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _detailSaving = MutableLiveData<SavingDetailItem?>()
    val detailSaving: LiveData<SavingDetailItem?> = _detailSaving

    private val _transaksiSaving = MutableLiveData<List<TransaksiPortoItem?>?>()
    val transaksiSaving: LiveData<List<TransaksiPortoItem?>?> = _transaksiSaving

    private val _inviteMessage = MutableLiveData<String?>()
    val inviteMessage: LiveData<String?> = _inviteMessage

    private val _deleteMessage = MutableLiveData<String?>()
    val deleteMessage: LiveData<String?> = _deleteMessage

    private val _editMessage = MutableLiveData<String?>()
    val editMessage: LiveData<String?> = _editMessage

    private val _deleteMessageTransaction = MutableLiveData<String?>()
    val deleteMessageTransaction: LiveData<String?> = _deleteMessageTransaction


    fun editSaving(
        id: Int,
        title: String,
        target: BigInteger,
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.editSaving(id, title, target)
                if (response.success == true) {
                    _editMessage.value = response.message
                    getDetailSaving(id)
                    _isLoading.value = false
                } else {
                    _editMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                _editMessage.value = "Unable to edit this saving plan"
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteSavingTrans(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.deleteSavingTrans(id)
                if (response.success == true) {
                    _deleteMessageTransaction.value = response.message
                    getDetailSaving(id)
                    _isLoading.value = false
                } else {
                    _deleteMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                _deleteMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }



    fun deleteSaving(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.deleteSaving(id)
                if (response.success == true) {
                    _deleteMessage.value = "Successfuly deleted"
                    _isLoading.value = false
                } else {
                    _deleteMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                _deleteMessage.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun inviteFriend(email: String, portoId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.inviteFriend(email, portoId)
                if (response.success == true) {
                    _inviteMessage.value = "Successfuly invited"
                    _isLoading.value = false
                } else {
                    _inviteMessage.value = response.message ?: "An error occurred"
                }
            } catch (e: Exception) {
                if (e.message?.contains("404") == true) {
                    _inviteMessage.value = "User not found"
                } else if (e.message?.contains("409") == true) {
                    _inviteMessage.value = "User is already in this saving plan"
                } else {
                    _inviteMessage.value = "Please check the email again"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun getDetailSaving(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getDetailSaving(id)
                Log.d("COY DetailSavingViewModel", "getDetailSaving: ${response}")
                if (response.success == true) {
                    _detailSaving.value = response.data
                    _transaksiSaving.value = response.data?.transaksiPorto
                    _isLoading.value = false
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