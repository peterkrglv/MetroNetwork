package com.example.learncompose.domain

class GetPostsUseCase(
    private val repo: PostRepository
) {
    suspend fun execute(station: String) = repo.getPosts(station)
}