package com.example.learncompose.ui.metro

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.learncompose.ui.elements.CircularButton
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

    when (val action = viewAction.value) {
        is MetroAction.NavigateToStation -> {
            rootController.push(
                "station",
                launchFlag = LaunchFlag.ClearPrevious,
                params = mapOf(
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
    val line: MutableState<MetroLine> = remember { mutableStateOf(state.line) }
    val lines = state.lines
    val primary = state.line.color

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
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
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(lines.size) { i ->
                    CircularButton(
                        onClick = {
                            line.value = lines[i]
                            onLineClick(lines[i])
                        },
                        color = lines[i].color
                    )
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
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(primary),
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
            MetroLine(0,"Сокольническая", Color.Red, emptyList()),
            MetroLine(0,"Замоскворецкая", Color(0xFF008000), emptyList()), // Green
            MetroLine(0,"Арбатско-Покровская", Color(0xFF000080), emptyList()), // Dark blue
            MetroLine(0,"Филёвская", Color(0xFFADD8E6), emptyList()), // Light blue
            MetroLine(0,"Кольцевая", Color(0xFFA52A2A), emptyList()), // Brown
            MetroLine(0,"Калужско-Рижская", Color(0xFFFFA500), emptyList()), // Orange
            MetroLine(0,"Таганско-Краснопресненская", Color(0xFF800080), emptyList()), // Violet
            MetroLine(0,"Калининская", Color(0xFFFFFF00), emptyList()), // Yellow
            MetroLine(0,"Серпуховско-Тимирязевская", Color(0xFF808080), emptyList()), // Grey
            MetroLine(0,"Люблинско-Дмитровская", Color(0xFF90EE90), emptyList()), // Salad
            MetroLine(0,"Каховская", Color(0xFF0000FF), emptyList()), // Blue
            MetroLine(0,"Бутовская", Color(0xFFB0E0E6), emptyList()) // Lighter blue
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
