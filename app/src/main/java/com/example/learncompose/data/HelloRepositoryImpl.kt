package com.example.learncompose.data

import android.util.Log
import com.example.learncompose.domain.HelloRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class HelloRepositoryImpl(
    private val client: OkHttpClient
) : HelloRepository {
    override suspend fun getHello(): String {
        val request = Request.Builder()
            .url("https://metro-server-rc8a.onrender.com/hello")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Запрос к серверу не был успешен:" +
                            " ${response.code()} ${response.message()}")
                }
                return response.body()!!.string()
            }
        } catch (e: IOException) {
            Log.d("OkHttp", "Some error occured ${e.message}")
            return ""
        }

    }
}