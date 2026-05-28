package com.our.emotions.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MilitaryTech
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.our.emotions.ui.navigation.AppScreen
import com.our.emotions.ui.navigation.toMainScreen

data class AppBottomBarItem(
    val label: String? = null,
    val icon: ImageVector,
    val selected: Boolean,
    val onClick: () -> Unit,
)

@Composable
fun AppBottomBar(
    items: List<AppBottomBarItem>,
    tonalElevation: Dp = 4.dp,
    containerColor: Color = MaterialTheme.colorScheme.surface,
) {
    NavigationBar(
        containerColor = containerColor,
        tonalElevation = tonalElevation
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick = item.onClick,
                icon = { androidx.compose.material3.Icon(item.icon, contentDescription = null) },
                label = item.label?.let { label -> { Text(label) } }
            )
        }
    }
}

@Composable
fun AppMainBottomBar(
    selected: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    tonalElevation: Dp = 4.dp,
    containerColor: Color = MaterialTheme.colorScheme.surface,
) {
    val mainSelected = selected.toMainScreen()
    AppBottomBar(
        items = listOf(
            AppBottomBarItem(
                label = "Início",
                icon = Icons.Rounded.Home,
                selected = mainSelected == AppScreen.Home,
                onClick = { onNavigate(AppScreen.Home) }
            ),
            AppBottomBarItem(
                label = "Jornada",
                icon = Icons.Rounded.Map,
                selected = mainSelected == AppScreen.Journey,
                onClick = { onNavigate(AppScreen.Journey) }
            ),
            AppBottomBarItem(
                label = "Medalhas",
                icon = Icons.Rounded.MilitaryTech,
                selected = mainSelected == AppScreen.Badges,
                onClick = { onNavigate(AppScreen.Badges) }
            ),
            AppBottomBarItem(
                label = "Perfil",
                icon = Icons.Rounded.Person,
                selected = mainSelected == AppScreen.Profile,
                onClick = { onNavigate(AppScreen.Profile) }
            ),
        ),
        tonalElevation = tonalElevation,
        containerColor = containerColor
    )
}
