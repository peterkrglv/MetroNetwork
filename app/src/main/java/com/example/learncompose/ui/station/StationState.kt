package com.example.learncompose.ui.station

import com.example.learncompose.domain.MetroLine
import com.example.learncompose.domain.Post

sealed class StationState {
    data object Idle : StationState()
    data class Main(
        val line: MetroLine,
        val station: String,
        val posts: List<Post>
    ) : StationState()

    data object Loading : StationState()
}

sealed class StationEvent {
    data class LoadData(val line: MetroLine, val station: String) : StationEvent()
    data object ReturnButtonClicked : StationEvent()
    data object AddButtonClicked : StationEvent()
    data object Clear : StationEvent()
}

sealed class StationAction {
    data object navigateToMetro : StationAction()
    data object navigateToAddPost : StationAction()
}