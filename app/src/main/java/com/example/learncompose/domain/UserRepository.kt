package com.example.learncompose.domain

interface UserRepository {
    suspend fun signup(username: String, email: String, password: String): Boolean
    suspend fun login(username: String, password: String): Boolean
}