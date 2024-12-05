package com.example.learncompose.ui.login

sealed class LoginState() {
    data object Idle : LoginState()
    data class Main(
        val email: String = "",
        val password: String = "",
        val passwordVisibility: Boolean = false,
        val isError: Boolean = false,
        val loading: Boolean = false
    ) : LoginState()
}

sealed class LoginEvent {
    data object ShowPasswordButtonClicked : LoginEvent()
    data class LoginButtonClicked(val email: String, val password: String) : LoginEvent()
    data class SignUpButtonClicked(val email: String, val password: String) : LoginEvent()
    data object Clear : LoginEvent()
}

sealed class LoginAction {
    data object NavigateToMetro : LoginAction()
    data object NavigateToSignUp : LoginAction()
}