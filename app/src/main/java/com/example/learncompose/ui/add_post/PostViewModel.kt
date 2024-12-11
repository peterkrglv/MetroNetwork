package com.example.learncompose.ui.add_post

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncompose.domain.GetPastLoginUseCase
import com.example.learncompose.domain.MetroLine
import com.example.learncompose.domain.UploadPostUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostViewModel(val getPastLoginUseCase: GetPastLoginUseCase, val uploadPostUseCase: UploadPostUseCase) : ViewModel() {
    private val _viewState = MutableStateFlow<PostState>(PostState.Idle)
    val viewState: StateFlow<PostState>
        get() = _viewState

    private val _viewAction = MutableStateFlow<PostAction?>(null)
    val viewAction: StateFlow<PostAction?>
        get() = _viewAction

    private val username = MutableStateFlow("")
    private val station = MutableStateFlow("")

    fun obtainEvent(event: PostEvent) {
        when (event) {
            is PostEvent.LoadData -> loadUser(event.station, event.line)
            is PostEvent.UploadPostButtonClicked -> uploadPostButtonClicked(
                username = event.username,
                station = event.station,
                text = event.text,
                photo = event.photo
            )

            is PostEvent.ReturnButtonClicked -> returnButtonClicked()
            is PostEvent.Clear -> clearAction()
        }
    }

    private fun loadUser(station: String, line: MetroLine) {
        _viewState.value = PostState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = getPastLoginUseCase.execute()
                if (user != null) {
                    username.value = user
                } else {
                    Log.e("PostViewModel", "User is null")
                }
                _viewState.value = PostState.Main(
                    username = username.value,
                    station = station,
                    line = line,
                    text = "",
                    photo = "",
                )
            }
        }
    }

    private fun clearAction() {
        _viewAction.value = null
    }

    private fun returnButtonClicked() {
        _viewAction.value = PostAction.NavigateToStation
    }

    private fun uploadPostButtonClicked(
        username: String,
        station: String,
        text: String,
        photo: Uri
    ) {
        _viewState.value = PostState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val isUploaded = uploadPostUseCase.execute(
                    username = username,
                    station = station,
                    text = text,
                    photo = photo
                )
                if (isUploaded) {
                    Log.d("PostViewModel", "Upload success")
                    _viewAction.value = PostAction.NavigateToStation
                } else {
                    Log.e("PostViewModel", "Upload failed")
                }
            }
        }
    }
}