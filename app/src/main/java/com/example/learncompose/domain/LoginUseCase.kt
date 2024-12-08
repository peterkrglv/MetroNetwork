package com.example.learncompose.domain

class LoginUseCase(
    private val repo: UserRepository,
    private val sharedPref: SharedPrefRepository
) {
    suspend fun execute(username: String, password: String): Boolean {
        val loginResult = repo.login(username, password)
        if (loginResult) {
            sharedPref.saveUser(username)
            return true
        }
        return false
    }
}