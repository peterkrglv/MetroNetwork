package com.example.learncompose.domain

interface LinesRepository {
    suspend fun loadData(): List<MetroLine>
}