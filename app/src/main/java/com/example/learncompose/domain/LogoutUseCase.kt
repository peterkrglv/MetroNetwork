package com.example.learncompose.domain

class LogoutUseCase(private val sharedPrefRepository: SharedPrefRepository) {
    suspend fun execute() {
        sharedPrefRepository.clearLogin()
    }
}