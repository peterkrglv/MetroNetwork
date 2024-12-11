package com.example.learncompose.domain

import androidx.compose.ui.graphics.Color

data class MetroLine(
    val num: Int,
    val name: String,
    val color: Color,
    val stations: List<String>
)
