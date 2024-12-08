package com.example.learncompose.data

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
}