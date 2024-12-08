package com.example.learncompose.domain

interface SharedPrefRepository {
    suspend fun saveUser(username: String)
    suspend fun readUser(): String?
    suspend fun clearLogin()
}