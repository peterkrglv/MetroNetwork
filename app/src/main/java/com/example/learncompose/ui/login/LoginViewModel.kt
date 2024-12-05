package com.example.learncompose.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncompose.domain.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val loginUseCase: LoginUseCase) : ViewModel() {
    private val _viewState = MutableStateFlow<LoginState>(LoginState.Main())
    val viewState: StateFlow<LoginState>
        get() = _viewState

    private val _viewAction = MutableStateFlow<LoginAction?>(null)
    val viewAction: StateFlow<LoginAction?>
        get() = _viewAction


    fun sendEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.ShowPasswordButtonClicked -> changeVisibilityState()
            is LoginEvent.LoginButtonClicked -> login(event.email, event.password)
            is LoginEvent.SignUpButtonClicked -> signup(event.email, event.password)
            is LoginEvent.Clear -> clearAction()
        }

    }

    private fun clearAction() {
        _viewAction.value = null
    }

    private fun changeVisibilityState() {
        if (_viewState.value !is LoginState.Main) return
        _viewState.value =
            (_viewState.value as LoginState.Main).copy(passwordVisibility = !(_viewState.value as LoginState.Main).passwordVisibility)
    }

    private fun login(email: String, password: String) {
        _viewState.value = (_viewState.value as LoginState.Main).copy(
            email = email,
            password = password,
            loading = true
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                val loginResult = loginUseCase.execute(email, password)
                if (loginResult) {
                    _viewState.value = LoginState.Main()
                    _viewAction.value = LoginAction.NavigateToMetro
                } else {
                    if (_viewState.value !is LoginState.Main) return@withContext
                    _viewState.value =
                        (_viewState.value as LoginState.Main).copy(isError = true, loading = false)
                }
            }
        }
    }

    private fun signup(email: String, password: String) {
        _viewState.value = LoginState.Main(email = email, password = password)
        _viewAction.value = LoginAction.NavigateToSignUp
    }
}