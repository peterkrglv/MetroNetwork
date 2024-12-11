package com.example.learncompose.domain

class LoadPostsUseCase(
    private val repo: PostRepository
) {
    suspend fun execute() = repo.getPosts()
}