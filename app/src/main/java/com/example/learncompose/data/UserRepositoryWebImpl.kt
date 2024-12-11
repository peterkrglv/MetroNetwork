package com.example.learncompose.data

import com.example.learncompose.domain.User
import com.example.learncompose.domain.UserRepository
import okhttp3.OkHttpClient
import okhttp3.Request

class UserRepositoryWebImpl(private val client: OkHttpClient) : UserRepository {
    override suspend fun signup(username: String, email: String, password: String): Boolean {
        val request = Request.Builder()
            .url("https://metro-server-rc8a.onrender.com/signup?username=$username&email=$email&password=$password")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw Exception(
                        "Запрос к серверу не был успешен:" +
                                " ${response.code} ${response.message}"
                    )
                }

                return true
            }
        } catch(e: Exception) {
            return false
        }
    }

    override suspend fun login(username: String, password: String): Boolean {
        val request = Request.Builder()
            .url("https://metro-server-rc8a.onrender.com/login?username=$username&password=$password")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw Exception(
                        "Запрос к серверу не был успешен:" +
                                " ${response.code} ${response.message}"
                    )
                }

                return true
            }
        } catch(e: Exception) {
            return false
        }
    }
}