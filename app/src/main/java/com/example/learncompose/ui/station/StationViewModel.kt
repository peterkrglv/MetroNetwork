package com.example.learncompose.ui.station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncompose.domain.LoadPostsUseCase
import com.example.learncompose.domain.MetroLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StationViewModel(private val loadPostsUseCase: LoadPostsUseCase) : ViewModel() {
    private val _viewState = MutableStateFlow<StationState>(StationState.Idle)
    val viewState: StateFlow<StationState>
        get() = _viewState

    private val _viewAction = MutableStateFlow<StationAction?>(null)
    val viewAction: StateFlow<StationAction?>
        get() = _viewAction

    fun obtainEvent(event: StationEvent) {
        when (event) {
            is StationEvent.LoadData -> loadData(event.line, event.station)
            is StationEvent.ReturnButtonClicked -> returnButtonClicked()
            is StationEvent.AddButtonClicked -> addButtonClicked()
            is StationEvent.Clear -> clearAction()
        }
    }

    private fun clearAction() {
        _viewAction.value = null
    }

    private fun addButtonClicked() {
        _viewAction.value = StationAction.navigateToAddPost
    }

    private fun returnButtonClicked() {
        _viewAction.value = StationAction.navigateToMetro
    }

    private fun loadData(line: MetroLine, station: String) {
        _viewState.value = StationState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val posts = loadPostsUseCase.execute()
                _viewState.value = StationState.Main(
                    line,
                    station,
                    posts
                )
            }
        }
    }
}