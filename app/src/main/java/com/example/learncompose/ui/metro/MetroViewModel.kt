package com.example.learncompose.ui.metro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncompose.domain.LoadLinesUseCase
import com.example.learncompose.domain.MetroLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MetroViewModel(
    private val loadLinesUseCase: LoadLinesUseCase
) : ViewModel() {
    private val _viewState = MutableStateFlow<MetroState>(MetroState.Idle)
    val viewState: StateFlow<MetroState>
        get() = _viewState

    private val _viewAction = MutableStateFlow<MetroAction?>(null)
    val viewAction: StateFlow<MetroAction?>
        get() = _viewAction

    fun obtainEvent(event: MetroEvent) {
        when (event) {
            is MetroEvent.LoadData -> loadData()
            is MetroEvent.ChangeLine -> changeLine(event.line)
            is MetroEvent.Clear -> clearAction()
            is MetroEvent.SelectStation -> selectStation(event.line, event.station)
        }
    }

    private fun selectStation(line: MetroLine, station: String) {
        _viewAction.value = MetroAction.NavigateToStation(line, station)

    }

    private fun clearAction() {
        _viewAction.value = null
    }

    private fun changeLine(line: MetroLine) {
        if (viewState.value !is MetroState.Main) return
        _viewState.value = (_viewState.value as MetroState.Main).copy(line = line)
    }

    private fun loadData() {
        _viewState.value = MetroState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                val lines = loadLinesUseCase.execute()
                _viewState.value = MetroState.Main(
                    line = lines[0],
                    lines = lines
                )
            }
        }
    }

}