package com.example.learncompose.ui.station

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.LearnComposeTheme
import com.example.learncompose.domain.MetroLine
import com.example.learncompose.domain.Post
import com.example.learncompose.ui.elements.LoadingScreen
import com.example.learncompose.ui.elements.PostCard
import org.koin.androidx.compose.koinViewModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun Station(
    params: Map<String, Any>,
    viewModel: StationViewModel = koinViewModel()
) {
    val rootController = LocalRootController.current
    val viewState = viewModel.viewState.collectAsState()
    val viewAction = viewModel.viewAction.collectAsState()

    val line = params["line"] as MetroLine
    val station = params["station"] as String

    when (viewAction.value) {
        is StationAction.navigateToMetro -> {
            rootController.push("metro", launchFlag = LaunchFlag.ClearPrevious)
            viewModel.obtainEvent(StationEvent.Clear)
        }

        is StationAction.navigateToAddPost -> {
            //передать параметры state.line, state.station
            rootController.push(
                "add_post",
                params = mapOf(
                    "line" to line,
                    "station" to station
                )
            )
            viewModel.obtainEvent(StationEvent.Clear)
        }

        null -> {}
    }

    when (val state = viewState.value) {
        is StationState.Idle -> {
            viewModel.obtainEvent(StationEvent.LoadData(line, station))
            StationIdle()
        }
        is StationState.Main -> MainState(
            state = state as StationState.Main,
            onReturnButtonClicked = { viewModel.obtainEvent(StationEvent.ReturnButtonClicked) },
            onAddButtonClicked = { viewModel.obtainEvent(StationEvent.AddButtonClicked) }
        )

        is StationState.Loading -> StationLoading()
    }
}

@Composable
fun StationLoading() {
    LoadingScreen()
}

@Composable
fun StationIdle() {
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainState(
    state: StationState.Main,
    onReturnButtonClicked: () -> Unit,
    onAddButtonClicked: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val primary = state.line.color

    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface//MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        state.station,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onReturnButtonClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Return button"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onAddButtonClicked) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add button",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(state.posts.size) { i ->
                val post: Post = state.posts[i]
                PostCard(post.username, post.date, post.text, post.photo, primary)
            }
        }
    }
}


@Preview(showSystemUi = true) //uiMode = 32
@Composable
fun StationPreview() {
    LearnComposeTheme {
        MainState(
            state = StationState.Main(
                MetroLine(1, "Сокольническая", Color.Red, emptyList()), "Охотный ряд", listOf(
                    Post("username", "Охотный ряд", "Some text", "01.01.2025", ""),
                    Post("username2", "Охотный ряд", "Some text", "01.01.2025", ""),
                    Post("username2", "Охотный ряд", "Some text", "01.01.2025", "")
                )
            ),
            onReturnButtonClicked = {},
            onAddButtonClicked = {}
        )
    }
}