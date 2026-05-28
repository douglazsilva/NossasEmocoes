package com.our.emotions.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import com.our.emotions.ui.components.AppMainBottomBar
import com.our.emotions.ui.screens.FeedbackRoute
import com.our.emotions.ui.screens.GameplayRoute
import com.our.emotions.ui.screens.BadgesRoute
import com.our.emotions.ui.screens.HomeRoute
import com.our.emotions.ui.screens.JourneyRoute
import com.our.emotions.ui.screens.LevelSelectionRoute
import com.our.emotions.ui.screens.ProfileRoute

@Composable
fun EmotionExplorerApp() {
    var screen by remember { mutableStateOf(AppScreen.Home) }

    val navigate: (AppScreen) -> Unit = navigate@{ destination ->
        if (destination == screen) return@navigate
        screen = destination
    }

    val handleBack: () -> Unit = {
        val main = screen.toMainScreen()
        when {
            screen != main -> screen = main
            screen != AppScreen.Home -> screen = AppScreen.Home
        }
    }

    BackHandler(enabled = screen != AppScreen.Home) {
        handleBack()
    }

    Scaffold(
        bottomBar = {
            AppMainBottomBar(selected = screen, onNavigate = navigate)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (screen) {
                AppScreen.Home -> HomeRoute(
                    onNavigate = navigate
                )
                AppScreen.Journey -> JourneyRoute(
                    onNavigate = navigate
                )
                AppScreen.LevelSelection -> LevelSelectionRoute(
                    onNavigate = navigate,
                    onBack = handleBack
                )
                AppScreen.Badges -> BadgesRoute(
                    onNavigate = navigate
                )
                AppScreen.Gameplay -> GameplayRoute(
                    onNavigate = navigate
                )
                AppScreen.Feedback -> FeedbackRoute(
                    onNavigate = navigate
                )
                AppScreen.Profile -> ProfileRoute(
                    onNavigate = navigate
                )
            }
        }
    }
}
