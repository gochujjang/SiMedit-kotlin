package com.codewithre.simedit.data

import com.codewithre.simedit.data.database.models.User
import com.codewithre.simedit.data.pref.UserPreference
import com.codewithre.simedit.data.remote.response.LoginResponse
import com.codewithre.simedit.data.remote.response.RegisterData
import com.codewithre.simedit.data.remote.response.RegisterResponse
import com.codewithre.simedit.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {

    suspend fun login(username: String, password: String) : LoginResponse {
        return withContext(Dispatchers.IO) {
            apiService.login(username, password)
        }
    }

    suspend fun saveSession(user: User) {
        withContext(Dispatchers.IO) {
            userPreference.saveSession(user)
        }
    }

    suspend fun register(
        name: String,
        username: String,
        email: String,
        password: String) : RegisterResponse {
        return withContext(Dispatchers.IO) {
            apiService.register(name, username, email, password)
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService,
        ) = AuthRepository(userPreference, apiService)
    }
}