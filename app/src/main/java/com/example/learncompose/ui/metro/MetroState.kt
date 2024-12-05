package com.example.learncompose.ui.metro

import com.example.learncompose.domain.MetroLine

sealed class MetroState {
    data object Idle : MetroState()
    data class Main(
        val line: MetroLine,
        val lines: List<MetroLine>
    ) : MetroState()

    data object Loading : MetroState()
}

sealed class MetroEvent {
    data object LoadData : MetroEvent()
    data class ChangeLine(val line: MetroLine) : MetroEvent()
    data object Clear : MetroEvent()
    data class SelectStation(val line: MetroLine, val station: String) : MetroEvent()
}

sealed class MetroAction {
    data class NavigateToStation(val line: MetroLine, val station: String) : MetroAction()
}