package com.example.learncompose.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncompose.domain.SignupUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel(val signupUseCase: SignupUseCase) : ViewModel() {
    private val _viewState = MutableStateFlow<SignupState>(SignupState.Main())
    val viewState: StateFlow<SignupState>
        get() = _viewState

    private val _viewAction = MutableStateFlow<SignupAction?>(null)
    val viewAction: StateFlow<SignupAction?>
        get() = _viewAction

    fun sendEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.ShowPasswordButtonClicked -> changeVisibilityState()
            is SignupEvent.ShowConfirmPasswordButtonClicked -> changeConfirmVisibilityState()
            is SignupEvent.SignupButtonClicked -> signup(
                event.email,
                event.username,
                event.password,
                event.confirmPassword
            )

            is SignupEvent.LoginButtonClicked -> login(
                event.username,
                event.email,
                event.password,
                event.confirmPassword
            )

            is SignupEvent.Clear -> clearAction()
        }

    }

    private fun clearAction() {
        _viewAction.value = null
    }

    private fun login(username: String, email: String, password: String, confirmPassword: String) {
        if (_viewState.value !is SignupState.Main) return
        _viewState.value = (_viewState.value as SignupState.Main).copy(
            username = username,
            email = email,
            password = password,
            confirmPassword = confirmPassword
        )
        _viewAction.value = SignupAction.NavigateToLogin
    }

    private fun signup(email: String, username: String, password: String, confirmPassword: String) {
        _viewState.value = (_viewState.value as SignupState.Main).copy(loading = true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                val signupResult = signupUseCase.execute(username, email, password, confirmPassword)
                if (signupResult) {
                    _viewState.value = SignupState.Main()
                    _viewAction.value = SignupAction.NavigateToMetro
                } else {
                    if (_viewState.value !is SignupState.Main) return@withContext
                    _viewState.value =
                        (_viewState.value as SignupState.Main).copy(isError = true, loading = false)
                }
            }
        }
    }

    private fun changeConfirmVisibilityState() {
        if (_viewState.value !is SignupState.Main) return
        _viewState.value = (_viewState.value as SignupState.Main).copy(
            confirmPasswordVisibility = !(_viewState.value as SignupState.Main).confirmPasswordVisibility
        )
    }

    private fun changeVisibilityState() {
        if (_viewState.value !is SignupState.Main) return
        _viewState.value = (_viewState.value as SignupState.Main).copy(
            passwordVisibility = !(_viewState.value as SignupState.Main).passwordVisibility
        )
    }
}
