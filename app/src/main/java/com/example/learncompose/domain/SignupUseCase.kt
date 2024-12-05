package com.example.learncompose.domain

class SignupUseCase(
    private val repo: UserRepository
) {
    suspend fun execute(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (password != confirmPassword) return false
        return repo.signup(username, email, password)
    }
}