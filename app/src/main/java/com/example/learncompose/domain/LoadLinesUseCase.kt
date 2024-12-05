package com.example.learncompose.domain

class LoadLinesUseCase(
    private val repo: LinesRepository
) {

    suspend fun execute() = repo.loadData()

}