package com.example.learncompose.domain

import androidx.compose.ui.graphics.Color

data class MetroLine(
    val name: String,
    val color: Color,
    val stations: List<String>
)
