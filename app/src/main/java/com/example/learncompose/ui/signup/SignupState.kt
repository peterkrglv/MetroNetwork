package com.example.learncompose.ui.signup

sealed class SignupState() {
    data object Idle : SignupState()
    data class Main(
        val username: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val passwordVisibility: Boolean = false,
        val confirmPasswordVisibility: Boolean = false,
        val isError: Boolean = false,
        val loading: Boolean = false
    ) : SignupState()
}

sealed class SignupEvent {
    data object ShowPasswordButtonClicked : SignupEvent()
    data object ShowConfirmPasswordButtonClicked : SignupEvent()
    data class SignupButtonClicked(
        val username: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : SignupEvent()

    data class LoginButtonClicked(
        val username: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : SignupEvent()

    data object Clear : SignupEvent()
}

sealed class SignupAction {
    data object NavigateToMetro : SignupAction()
    data object NavigateToLogin : SignupAction()
}