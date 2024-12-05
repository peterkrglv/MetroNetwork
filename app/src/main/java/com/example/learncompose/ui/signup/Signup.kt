package com.example.learncompose.ui.signup

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
fun Signup(
    viewModel: SignupViewModel = koinViewModel()
) {
    val rootController = LocalRootController.current
    val viewState = viewModel.viewState.collectAsState()
    val viewAction = viewModel.viewAction.collectAsState()


    when (viewAction.value) {
        is SignupAction.NavigateToMetro -> {
            rootController.push("metro", launchFlag = LaunchFlag.ClearPrevious)
            viewModel.sendEvent(SignupEvent.Clear)
        }

        is SignupAction.NavigateToLogin -> {
            rootController.popBackStack()
            viewModel.sendEvent(SignupEvent.Clear)
        }

        null -> {}

    }


    MainState(
        state = (viewState.value as SignupState.Main),
        onEyeIconClick = { viewModel.sendEvent(SignupEvent.ShowPasswordButtonClicked) },
        onConfirmEyeIconClick = { viewModel.sendEvent(SignupEvent.ShowConfirmPasswordButtonClicked) },
        onSignupClick = { username, email, password, confirmPassword ->
            viewModel.sendEvent(
                SignupEvent.SignupButtonClicked(username, email, password, confirmPassword)
            )
        },
        onLoginClick = { username, email, password, confirmPassword ->
            viewModel.sendEvent(
                SignupEvent.LoginButtonClicked(
                    username,
                    email,
                    password,
                    confirmPassword
                )
            )
        }
    )
}

@Composable
fun MainState(
    state: SignupState.Main,
    onEyeIconClick: () -> Unit,
    onConfirmEyeIconClick: () -> Unit,
    onSignupClick: (String, String, String, String) -> Unit,
    onLoginClick: (String, String, String, String) -> Unit
) {
    val email: MutableState<String> = remember { mutableStateOf(state.email) }
    val username: MutableState<String> = remember { mutableStateOf(state.username) }
    val password: MutableState<String> = remember { mutableStateOf(state.password) }
    val confirmPassword: MutableState<String> = remember { mutableStateOf(state.confirmPassword) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        MyTitle("Sign up")

        MyTextField(
            value = username.value,
            label = "Username",
            isError = state.isError,
            onValueChange = { username.value = it }
        )

        MyTextField(
            value = email.value,
            label = "Email",
            isError = state.isError,
            onValueChange = { email.value = it }
        )

        MyPasswordField(
            value = password.value,
            label = "Password",
            isError = state.isError,
            onValueChange = { password.value = it },
            onIconClick = onEyeIconClick,
            passwordVisibility = state.passwordVisibility
        )

        MyPasswordField(
            value = confirmPassword.value,
            label = "Confirm password",
            isError = state.isError,
            onValueChange = { confirmPassword.value = it },
            onIconClick = onConfirmEyeIconClick,
            passwordVisibility = state.confirmPasswordVisibility
        )

        Button(
            modifier = Modifier,
            onClick = {
                onSignupClick(
                    username.value,
                    email.value,
                    password.value,
                    confirmPassword.value
                )
            }
        ) {
            Text("Sign up")
        }

        TextButton(
            modifier = Modifier,
            onClick = {
                onLoginClick(
                    username.value,
                    email.value,
                    password.value,
                    confirmPassword.value
                )
            }
        ) {
            Text("Log in")
        }
    }
    if (state.loading) {
        LoadingBlock()
    }
}


@Preview(showSystemUi = true)
@Composable
fun SignupPreview() {
    LearnComposeTheme {
        MainState(
            state = SignupState.Main(),
            onEyeIconClick = {},
            onConfirmEyeIconClick = {},
            onSignupClick = { _, _, _, _ -> },
            onLoginClick = { _, _, _, _ -> }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingPreview() {
    LearnComposeTheme {
        MainState(
            state = SignupState.Main(loading = true),
            onEyeIconClick = {},
            onConfirmEyeIconClick = {},
            onSignupClick = { _, _, _, _ -> },
            onLoginClick = { _, _, _, _ -> }
        )
    }
}