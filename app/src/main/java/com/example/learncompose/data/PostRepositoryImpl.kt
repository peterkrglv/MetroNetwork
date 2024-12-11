package com.example.learncompose.data

import android.net.Uri
import com.example.learncompose.domain.Post
import com.example.learncompose.domain.PostRepository

class PostRepositoryImpl : PostRepository {

    override suspend fun getPosts(): List<Post> {
        return listOf(
            Post("username1", "Комсомольская", "Очень интересное описание", "01.01.2025", ""),
            Post("username2", "Комсомольская", "Очень интересное описание", "01.01.2025", ""),
            Post("username1", "Комсомольская", "Очень интересное описание", "01.01.2025", ""),
            Post("username1", "Комсомольская", "Очень интересное описание", "01.01.2025", ""),
            Post("username1", "Комсомольская", "Очень \n\n\nинтересное\n\n\n\n\n\n\n описание", "01.01.2025", "")
        )
    }

    override suspend fun uploadPost(
        username: String,
        station: String,
        text: String,
        photo: Uri
    ): Boolean {
        return true
    }
}