package com.example.learncompose.data

import com.example.learncompose.domain.User
import com.example.learncompose.domain.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun signup(username: String, email: String, password: String): Boolean {
        return false
    }

    override suspend fun login(username: String, password: String): Boolean {
        return true
    }
}