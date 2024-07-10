package com.codewithre.simedit.data.database.models

data class User(
    val username: String,
    val token: String,
    val isLogin: Boolean = false
)
