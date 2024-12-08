package com.example.learncompose.domain

interface PostRepository {
    suspend fun getPosts(): List<Post>
}