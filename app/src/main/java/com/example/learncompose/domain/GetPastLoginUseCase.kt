package com.example.learncompose.domain

class GetPastLoginUseCase(
    private val repo: SharedPrefRepository
) {
    suspend fun execute(): String? {
        val username = repo.readUser()
        return username
    }
}