package com.example.learncompose.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.learncompose.domain.SharedPrefRepository

class SharedPrefRepositoryImpl(context: Context) : SharedPrefRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    override suspend fun saveUser(username: String) {
        sharedPreferences.edit().putString("username", username).apply()
    }

    override suspend fun readUser(): String? {
        return sharedPreferences.getString("username", null)
    }

    override suspend fun clearLogin() {
        sharedPreferences.edit().clear().apply()
    }
}