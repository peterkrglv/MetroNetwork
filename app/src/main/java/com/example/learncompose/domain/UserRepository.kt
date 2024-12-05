package com.example.learncompose.domain

interface UserRepository {
    suspend fun signup(username: String, email: String, password: String): Boolean
    suspend fun login(email: String, password: String): Boolean
    suspend fun logout()
    suspend fun getUser(): User?
}