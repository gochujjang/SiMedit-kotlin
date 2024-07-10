package com.codewithre.simedit.di

import android.content.Context
import com.codewithre.simedit.data.AuthRepository
import com.codewithre.simedit.data.pref.UserPreference
import com.codewithre.simedit.data.pref.dataStore
import com.codewithre.simedit.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object AuthInjection {
    fun provideRepository(context: Context) : AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return AuthRepository.getInstance(pref, apiService)
    }
}