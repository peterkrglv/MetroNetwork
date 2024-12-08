package com.example.learncompose.navigation

import com.example.learncompose.ui.login.Login
import com.example.learncompose.ui.metro.Metro
import com.example.learncompose.ui.signup.Signup
import com.example.learncompose.ui.station.Station
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder


fun RootComposeBuilder.navigationGraph() {
    screen(name = "metro") {
        Metro()
    }
    screen("login") {
        Login()
    }
    screen("signup") {
        Signup()
    }
    screen("station") {
        Station(params = it as Map<String, Any>)
    }
}