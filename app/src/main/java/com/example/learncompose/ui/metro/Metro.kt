package com.example.learncompose.ui.metro

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.LearnComposeTheme
import com.example.learncompose.domain.MetroLine
import com.example.learncompose.ui.elements.LoadingScreen
import com.example.learncompose.ui.icons.EndStationIC
import com.example.learncompose.ui.icons.Logout
import com.example.learncompose.ui.icons.StationIC
import org.koin.androidx.compose.koinViewModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun Metro(
    viewModel: MetroViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsState()
    val viewAction = viewModel.viewAction.collectAsState()
    val rootController = LocalRootController.current
    val username = "User" //TODO: change... that...

    when (val action = viewAction.value) {
        is MetroAction.NavigateToStation -> {
            rootController.push(
                "station",
                launchFlag = LaunchFlag.ClearPrevious,
                params = mapOf(
                    "username" to username,
                    "line" to action.line,
                    "station" to action.station
                )
            )
            viewModel.obtainEvent(MetroEvent.Clear)
        }
        is MetroAction.LogOut -> {
            viewModel.obtainEvent(MetroEvent.Clear)
            rootController.push("login", launchFlag = LaunchFlag.ClearPrevious)
        }

        null -> {}
    }



    LearnComposeTheme {
        when (val state = viewState.value) {
            is MetroState.Idle -> {
                viewModel.obtainEvent(MetroEvent.LoadData)
                MetroIdle()
            }

            is MetroState.Main -> MainState(
                state = state,
                onLineClick = { viewModel.obtainEvent(MetroEvent.ChangeLine(it)) },
                onStationClick = { line, station ->
                    viewModel.obtainEvent(
                        MetroEvent.SelectStation(
                            line,
                            station
                        )
                    )
                },
                onSearchButtonClicked = { viewModel.obtainEvent(MetroEvent.SearchButtonClicked) },
                onLogOutClick = {
                    viewModel.obtainEvent(MetroEvent.LogOut)
                }
            )

            is MetroState.Loading -> MetroLoading()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainState(
    state: MetroState.Main,
    onLineClick: (MetroLine) -> Unit,
    onStationClick: (MetroLine, String) -> Unit,
    onSearchButtonClicked: () -> Unit,
    onLogOutClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val darken: Float = isSystemInDarkTheme().let { if (it) 0.1f else 0.5f }
    val line: MutableState<MetroLine> = remember { mutableStateOf(state.line) }
    val lines = state.lines
    val primary = Color(
        (state.line.color.red - darken).let { if (it < 0) 0f else it },
        (state.line.color.green - darken).let { if (it < 0) 0f else it },
        (state.line.color.blue - darken).let { if (it < 0) 0f else it },
        state.line.color.alpha - 0.2f
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface//MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        state.line.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onLogOutClick()
                        line.value = lines[0]
                    }) {
                        Icon(
                            //log out button icon from drawable
                            imageVector = Logout,
                            contentDescription = "Log out button"
                        )
                    }
                },
                actions = {
//                    IconButton(onClick = { "onSettingsButtonClicked" }) {
//                        Icon(
//                            imageVector = Icons.Filled.Add,
//                            contentDescription = "Add button",
//                            tint = MaterialTheme.colorScheme.onSurface
//                        )
//                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            LazyRow(
                // Adjust rows as needed
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(lines.size) { i ->
                    Canvas(
                        Modifier
                            .size(40.dp)
                            .padding(4.dp)
                            .clickable {
                                line.value = lines[i]
                                onLineClick(lines[i])
                            }
                    ) {
                        drawCircle(color = lines[i].color)
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(line.value.stations.size) { station ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .clickable {
                            onStationClick(line.value, line.value.stations[station])
                        }
                ) {
                    Image(
                        if (station == 0 || station == line.value.stations.lastIndex) EndStationIC else StationIC,
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(state.line.color),
                        contentDescription = null,
                        modifier = if (station == line.value.stations.lastIndex) Modifier.rotate(
                            180.0f
                        ) else Modifier
                    )
                    Text(text = line.value.stations[station])
                }
            }
        }
    }
}

@Composable
fun MetroIdle() {
}


@Composable
fun MetroLoading() {
    LoadingScreen()
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Loading"
)
@Composable
fun MetroLoadingPreview() {
    MetroLoading()
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMetroMain() {
    val metroLines = mutableListOf<MetroLine>()
    //fill with moscow metro lines
    metroLines.addAll(
        listOf(
            MetroLine("Сокольническая", Color.Red, emptyList()),
            MetroLine("Замоскворецкая", Color(0xFF008000), emptyList()), // Green
            MetroLine("Арбатско-Покровская", Color(0xFF000080), emptyList()), // Dark blue
            MetroLine("Филёвская", Color(0xFFADD8E6), emptyList()), // Light blue
            MetroLine("Кольцевая", Color(0xFFA52A2A), emptyList()), // Brown
            MetroLine("Калужско-Рижская", Color(0xFFFFA500), emptyList()), // Orange
            MetroLine("Таганско-Краснопресненская", Color(0xFF800080), emptyList()), // Violet
            MetroLine("Калининская", Color(0xFFFFFF00), emptyList()), // Yellow
            MetroLine("Серпуховско-Тимирязевская", Color(0xFF808080), emptyList()), // Grey
            MetroLine("Люблинско-Дмитровская", Color(0xFF90EE90), emptyList()), // Salad
            MetroLine("Каховская", Color(0xFF0000FF), emptyList()), // Blue
            MetroLine("Бутовская", Color(0xFFB0E0E6), emptyList()) // Lighter blue
        )
    )
    MainState(
        MetroState.Main(
            line = metroLines[0],
            lines = metroLines,
        ),
        onStationClick = { _, _ -> },
        onLineClick = {},
        onSearchButtonClicked = {},
        onLogOutClick = {}
    )
}
