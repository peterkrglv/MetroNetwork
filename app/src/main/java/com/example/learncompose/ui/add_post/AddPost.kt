package com.example.learncompose.ui.add_post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.learncompose.R
import com.example.learncompose.domain.MetroLine
import com.example.learncompose.ui.elements.EditPost
import com.example.learncompose.ui.elements.LoadingScreen
import org.koin.androidx.compose.koinViewModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag


@Composable
fun AddPost(
    params: Map<String, Any>,
    viewModel: PostViewModel = koinViewModel()
) {
    val rootController = LocalRootController.current
    val viewState = viewModel.viewState.collectAsState()
    val viewAction = viewModel.viewAction.collectAsState()

    val line = params["line"] as MetroLine
    val station = params["station"] as String

    when (viewAction.value) {
        is PostAction.NavigateToStation -> {
            rootController.push(
                "station",
                mapOf("line" to line, "station" to station),
                launchFlag = LaunchFlag.ClearPrevious
            )
            viewModel.obtainEvent(PostEvent.Clear)
        }

        null -> {}
    }

    when (val state = viewState.value) {
        is PostState.Idle -> {
            PostIdle()
            viewModel.obtainEvent(PostEvent.LoadData(station, line))
        }

        is PostState.Main -> MainState(
            state = state as PostState.Main,
            onReturnButtonClicked = { viewModel.obtainEvent(PostEvent.ReturnButtonClicked) },
            onUploadPostButtonClicked = { text, photo ->
                viewModel.obtainEvent(
                    PostEvent.UploadPostButtonClicked(
                        username = state.username,
                        station = state.station,
                        text = text,
                        photo = photo
                    )
                )
            }
        )

        is PostState.Loading -> PostLoading()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainState(
    state: PostState.Main,
    onReturnButtonClicked: () -> Unit,
    onUploadPostButtonClicked: (String, Uri) -> Unit
) {
    val username = state.username
    val primary = state.line.color
    val text: MutableState<String> = remember { mutableStateOf(state.text) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    var selectedImageUri by remember { mutableStateOf<Uri?>(Uri.parse("android.resource://com.example.learncompose/" + R.drawable.upload)) }
    val getContent =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
        }

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
                        "Новый пост",
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
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            EditPost(
                username = username,
                text = text.value,
                image = selectedImageUri
                    ?: Uri.parse("android.resource://com.example.learncompose/" + R.drawable.upload),
                color = primary,
                onTextValueChange = { text.value = it },
                onButtonClick = { onUploadPostButtonClicked(text.value, selectedImageUri!!) },
                onImageClick = {
                    getContent.launch("image/*")
                },
            )
        }
    }

}


@Composable
fun PostLoading() {
    LoadingScreen()
}

@Composable
fun PostIdle() {
}


@Preview(showBackground = true)
@Composable
fun AddPostPreview() {
    MainState(
        state = PostState.Main(
            username = "username",
            station = "station",
            text = "",
            photo = "photo",
            line = MetroLine(
                num = 0,
                name = "line",
                color = Color.Red,
                stations = emptyList()
            )
        ),
        onReturnButtonClicked = {},
        onUploadPostButtonClicked = {_, _ -> }
    )
}