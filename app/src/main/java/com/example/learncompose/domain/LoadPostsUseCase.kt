package com.example.learncompose.domain

import com.example.learncompose.data.PostRepositoryImpl

class LoadPostsUseCase(
    private val repo: PostRepositoryImpl
) {

    suspend fun execute() = repo.getPosts()

}