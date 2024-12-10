package com.example.learncompose.domain

class GetHelloUseCase(
    private val repo: HelloRepository
) {
    suspend fun execute() = repo.getHello()
}