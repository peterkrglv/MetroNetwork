package com.example.learncompose.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncompose.domain.CheckPastLoginUseCase
import com.example.learncompose.domain.GetHelloUseCase
import com.example.learncompose.domain.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val checkPastLoginUseCase: CheckPastLoginUseCase,
    private val getHelloUseCase: GetHelloUseCase
) : ViewModel() {
    private val _viewState = MutableStateFlow<LoginState>(LoginState.Idle)
    val viewState: StateFlow<LoginState>
        get() = _viewState

    private val _viewAction = MutableStateFlow<LoginAction?>(null)
    val viewAction: StateFlow<LoginAction?>
        get() = _viewAction


    fun sendEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.CheckPastLogin -> checkPastLogin()
            is LoginEvent.ShowPasswordButtonClicked -> changeVisibilityState()
            is LoginEvent.LoginButtonClicked -> login(event.username, event.password)
            is LoginEvent.SignUpButtonClicked -> signup(event.username, event.password)
            is LoginEvent.Clear -> clearAction()
        }

    }

    private fun checkPastLogin() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                Log.d("OkHttp", getHelloUseCase.execute())
//            }
//        }
        _viewState.value = LoginState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val username = checkPastLoginUseCase.execute()
                Log.d("wtf", "username: $username")
                if (username != null) {
                    _viewAction.value = LoginAction.NavigateToMetro
                } else {
                    _viewState.value = LoginState.Main()
                }
            }
        }
    }

    private fun clearAction() {
        _viewAction.value = null
        _viewState.value = LoginState.Idle
    }

    private fun changeVisibilityState() {
        if (_viewState.value !is LoginState.Main) return
        _viewState.value =
            (_viewState.value as LoginState.Main).copy(passwordVisibility = !(_viewState.value as LoginState.Main).passwordVisibility)
    }

    private fun login(username: String, password: String) {
        _viewState.value = (_viewState.value as LoginState.Main).copy(
            username = username,
            password = password,
            loading = true
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000)
                val loginResult = loginUseCase.execute(username, password)
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

    private fun signup(username: String, password: String) {
        _viewState.value = LoginState.Main(username = username, password = password)
        _viewAction.value = LoginAction.NavigateToSignUp
    }
}