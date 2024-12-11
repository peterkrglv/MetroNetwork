package com.example.learncompose.domain

import android.net.Uri

class UploadPostUseCase(
    private val repo: PostRepository
) {
    suspend fun execute(
        username: String,
        station: String,
        text: String,
        photo: Uri
    ) = repo.uploadPost(username, station, text, photo)
}