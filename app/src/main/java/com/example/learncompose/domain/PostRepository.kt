package com.example.learncompose.domain

import android.net.Uri

interface PostRepository {
    suspend fun getPosts(station: String): List<Post>
    suspend fun uploadPost(username: String, station: String, text: String, photo: Uri): Boolean
}