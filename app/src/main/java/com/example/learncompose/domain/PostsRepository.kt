package com.example.learncompose.domain

interface PostsRepository {
    suspend fun getPosts(): List<Post>
}