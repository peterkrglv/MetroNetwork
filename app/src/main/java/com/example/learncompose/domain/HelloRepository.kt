package com.example.learncompose.domain

interface HelloRepository {
    suspend fun getHello(): String
}