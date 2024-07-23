package com.codewithre.simedit.ui.savings.listmember

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithre.simedit.data.UserRepository
import com.codewithre.simedit.data.remote.response.MemberItem
import kotlinx.coroutines.launch

class ListMemberViewModel(private val repository: UserRepository) : ViewModel()  {
    private val _listMember = MutableLiveData<List<MemberItem?>?>()
    val listMember: LiveData<List<MemberItem?>?> = _listMember

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getListMember(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getListMember(id)
                if (response.success == true) {
                    _listMember.value = response.data
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