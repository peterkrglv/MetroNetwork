package com.example.learncompose.ui.add_post

import android.net.Uri
import com.example.learncompose.domain.MetroLine

sealed class PostState {
    data object Idle : PostState()
    data class Main(
        val username: String,
        val line: MetroLine,
        val station: String,
        val text: String,
        val photo: String,
    ) : PostState()
    data object Loading : PostState()
}

sealed class PostEvent {
    data class LoadData(val station: String, val line: MetroLine): PostEvent()
    data class UploadPostButtonClicked(
        val username: String,
        val station: String,
        val text: String,
        val photo: Uri,
    ) : PostEvent()
    data object ReturnButtonClicked : PostEvent()
    data object Clear : PostEvent()
}

sealed class PostAction {
    data object NavigateToStation : PostAction()
}