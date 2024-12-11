package com.example.learncompose.ui.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.LearnComposeTheme
import com.example.learncompose.ui.elements.LoadingBlock
import com.example.learncompose.ui.elements.MyPasswordField
import com.example.learncompose.ui.elements.MyTextField
import com.example.learncompose.ui.elements.MyTitle
import org.koin.androidx.compose.koinViewModel
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag

@Composable
fun Login(
    viewModel: LoginViewModel = koinViewModel()
) {
    val rootController = LocalRootController.current
    val viewState = viewModel.viewState.collectAsState()
    val viewAction = viewModel.viewAction.collectAsState()

    Log.d("logout crash", "viewAction: $viewAction, viewState: $viewState")
    when (viewAction.value) {
        is LoginAction.NavigateToMetro -> {
            rootController.push("metro", launchFlag = LaunchFlag.ClearPrevious)
            viewModel.sendEvent(LoginEvent.Clear)
        }

        is LoginAction.NavigateToSignUp -> {
            rootController.push("signup")
            viewModel.sendEvent(LoginEvent.Clear)
        }

        null -> {}
    }

    LearnComposeTheme {
        when (viewState.value) {
            is LoginState.Idle -> {
                IdleState()
                viewModel.sendEvent(LoginEvent.CheckPastLogin)
            }

            is LoginState.Loading -> {
                LoadingState()
            }

            is LoginState.Main -> {
                MainState(
                    viewState.value as LoginState.Main,
                    onEyeIconClick = {
                        viewModel.sendEvent(LoginEvent.ShowPasswordButtonClicked)
                    },
                    onSignUpClick = { username, password ->
                        viewModel.sendEvent(LoginEvent.SignUpButtonClicked(username, password))
                    },
                    onLoginClick = { username, password ->
                        viewModel.sendEvent(LoginEvent.LoginButtonClicked(username, password))
                    })
            }
        }
    }
}

@Composable
fun MainState(
    state: LoginState.Main,
    onLoginClick: (String, String) -> Unit,
    onSignUpClick: (String, String) -> Unit,
    onEyeIconClick: () -> Unit
) {
    val username: MutableState<String> = remember { mutableStateOf(state.username) }
    val password: MutableState<String> = remember { mutableStateOf(state.password) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MyTitle(
            text = "Log in"
        )

        MyTextField(
            value = username.value,
            label = "Username",
            isError = state.isError,
            onValueChange = {
                username.value = it
            }
        )

        MyPasswordField(
            value = password.value,
            label = "Password",
            isError = state.isError,
            onValueChange = {
                password.value = it
            },
            onIconClick = onEyeIconClick,
            passwordVisibility = state.passwordVisibility
        )

        Button(
            modifier = Modifier,
            onClick = { onLoginClick(username.value, password.value) }
        ) {
            Text("Log in")
        }

        TextButton(
            modifier = Modifier,
            onClick = { onSignUpClick(username.value, password.value) }
        ) {
            Text("Sign up")
        }
    }

    if (state.loading) {
        LoadingBlock()
    }
}


@Composable
fun LoadingState() {
    LoadingBlock()
}


@Composable
fun IdleState() {
    LoadingBlock()
}


@Preview(showSystemUi = true)
@Composable
fun LogInPreview() {
    LearnComposeTheme {
        MainState(
            LoginState.Main(),
            onEyeIconClick = {},
            onSignUpClick = { _, _ -> },
            onLoginClick = { _, _ -> })
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingPreview() {
    LearnComposeTheme {
        MainState(
            LoginState.Main(loading = true),
            onEyeIconClick = {},
            onSignUpClick = { _, _ -> },
            onLoginClick = { _, _ -> })
    }
}