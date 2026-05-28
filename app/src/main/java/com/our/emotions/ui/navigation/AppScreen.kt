package com.our.emotions.ui.navigation

enum class AppScreen {
    Home,
    Journey,
    LevelSelection,
    Badges,
    Gameplay,
    Feedback,
    Profile,
}

fun AppScreen.toMainScreen(): AppScreen = when (this) {
    AppScreen.Gameplay,
    AppScreen.Feedback,
    -> AppScreen.LevelSelection
    else -> this
}
