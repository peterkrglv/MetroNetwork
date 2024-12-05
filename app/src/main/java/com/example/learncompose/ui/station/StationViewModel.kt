package com.example.learncompose.ui.station

import androidx.lifecycle.ViewModel
import com.example.learncompose.domain.MetroLine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StationViewModel : ViewModel() {
    private val _viewState = MutableStateFlow<StationState>(StationState.Idle)
    val viewState: StateFlow<StationState>
        get() = _viewState

    private val _viewAction = MutableStateFlow<StationAction?>(null)
    val viewAction: StateFlow<StationAction?>
        get() = _viewAction

    private lateinit var line: MetroLine

    fun obtainEvent(event: StationEvent) {
        when (event) {
            is StationEvent.LoadData -> loadData()
            is StationEvent.ReturnButtonClicked -> returnButtonClicked()
            is StationEvent.AddButtonClicked -> addButtonClicked()
            is StationEvent.Clear -> clearAction()
        }
    }

    private fun clearAction() {
        _viewAction.value = null
    }

    private fun addButtonClicked() {
        TODO("Not yet implemented")
    }

    private fun returnButtonClicked() {
        _viewAction.value = StationAction.navigateToMetro
    }

    private fun loadData() {
//        _viewState.value = StationState.Loading
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                Thread.sleep(2000)
//                val posts = loadPostsUseCase.execute()
//                _viewState.value = StationState.Main(
//                    line,
//                    station
//                )
//            }
//        }
    }
}