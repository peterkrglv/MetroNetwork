package com.example.learncompose.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import com.example.learncompose.navigation.navigationGraph
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.StartScreen
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent

@Composable
fun MainView(activity: ComponentActivity) {
    val odysseyConfiguration = OdysseyConfiguration(
        canvas = activity,
        startScreen = StartScreen.Custom("login")
    )

    setNavigationContent(odysseyConfiguration, onApplicationFinish = {
        activity.finishAffinity()
    }) {
        navigationGraph()
    }
}