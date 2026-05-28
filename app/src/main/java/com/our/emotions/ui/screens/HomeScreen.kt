package com.our.emotions.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.Token
import androidx.compose.material.icons.rounded.Whatshot
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.our.emotions.domain.model.StatItem
import com.our.emotions.ui.components.AppTopBar
import com.our.emotions.ui.components.DailyQuestCard
import com.our.emotions.ui.components.PrimaryActionButton
import com.our.emotions.ui.components.ProgressSnapshotCard
import com.our.emotions.ui.components.StatCard
import com.our.emotions.ui.navigation.AppScreen
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.EmotionTheme
import com.our.emotions.ui.theme.TextPrimaryLight
import com.our.emotions.ui.theme.TextSecondaryLight
import com.our.emotions.ui.viewmodel.HomeUiState
import com.our.emotions.ui.viewmodel.HomeViewModel
import com.our.emotions.ui.viewmodel.ProgressSnapshotUiState

@Composable
fun HomeRoute(
    onNavigate: (AppScreen) -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refresh()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onNavigate = onNavigate,
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onNavigate: (AppScreen) -> Unit,
) {
    JourneyHomeContent(
        uiState = uiState,
        onNavigate = onNavigate,
    )
}

@Composable
private fun HomeTopBar() {
    AppTopBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .shadow(6.dp, CircleShape)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(BluePrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "\uD83D\uDC66", fontSize = 16.sp)
                    }
                }
                Text(
                    text = "Explorador de Emoções",
                    style = MaterialTheme.typography.titleLarge,
                    color = BluePrimary
                )
            }
        }
    )
}

@Composable
internal fun JourneyHomeContent(
    uiState: HomeUiState,
    onNavigate: (AppScreen) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { HomeTopBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            WelcomeHeader(
                title = uiState.welcomeTitle,
                subtitle = uiState.welcomeSubtitle,
            )
            PrimaryActionButton(
                text = "COMEÇAR JORNADA",
                onClick = { onNavigate(AppScreen.LevelSelection) },
                height = 64.dp,
                shape = CircleShape,
                showShadow = true,
                trailingIcon = Icons.Rounded.PlayCircle
            )
            DailyQuestCard()
            ProgressSnapshotCard(
                snapshot = uiState.progressSnapshot,
                onViewAllBadges = { onNavigate(AppScreen.Badges) }
            )
            QuickStatsGrid(stats = uiState.stats)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
internal fun WelcomeHeader(
    title: String,
    subtitle: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(BluePrimary.copy(alpha = 0.08f))
            .padding(18.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = TextPrimaryLight
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight
            )
        }
        Box(
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.BottomEnd)
                .offset(x = -2.dp, y = 6.dp)
                .background(AccentBlue.copy(alpha = 0.2f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.TopEnd)
                .rotate(10f)
                .shadow(6.dp, CircleShape)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "\uD83C\uDF1F", fontSize = 30.sp)
        }
    }
}

@Composable
internal fun QuickStatsGrid(stats: List<StatItem>) {
    val icons = listOf(Icons.Rounded.Whatshot, Icons.Rounded.Token)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stats.forEachIndexed { index, stat ->
            StatCard(
                modifier = Modifier.weight(1f),
                icon = icons.getOrNull(index) ?: Icons.Rounded.Token,
                iconTint = stat.tint,
                title = stat.title,
                label = stat.label
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    EmotionTheme {
        HomeScreen(
            uiState = HomeUiState(
                welcomeTitle = "Oi, Explorador!",
                welcomeSubtitle = "Pronto para descobrir novos sentimentos hoje?",
                stats = listOf(
                    StatItem(title = "5 dias", label = "Sequência atual", tint = AccentAmber),
                    StatItem(title = "1.240", label = "Pontos totais", tint = AccentBlue),
                ),
                progressSnapshot = ProgressSnapshotUiState(
                    discoveredCount = 3,
                    totalCount = 10,
                    progress = 0.3f,
                    badgePreview = previewBadges(),
                    extraBadgeCount = 5,
                ),
            ),
            onNavigate = {},
        )
    }
}

private fun previewBadges(): List<com.our.emotions.ui.viewmodel.BadgeUiModel> {
    return com.our.emotions.ui.viewmodel.buildSessionBadges(1).take(2)
}
