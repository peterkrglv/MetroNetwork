package com.example.learncompose.domain

class LoginUseCase(
    private val repo: UserRepository
) {
    suspend fun execute(email: String, password: String) = repo.login(email, password)
}