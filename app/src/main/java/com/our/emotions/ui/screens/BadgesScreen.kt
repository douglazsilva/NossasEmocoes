package com.our.emotions.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Mood
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.our.emotions.ui.navigation.AppScreen
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.AccentPurple
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.EmotionTheme
import com.our.emotions.ui.theme.TextSecondaryLight
import com.our.emotions.ui.viewmodel.BadgeStatus
import com.our.emotions.ui.viewmodel.BadgeUiModel
import com.our.emotions.ui.viewmodel.BadgesUiState
import com.our.emotions.ui.viewmodel.BadgesViewModel

@Composable
fun BadgesRoute(
    onNavigate: (AppScreen) -> Unit,
    viewModel: BadgesViewModel = viewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    BadgesScreen(
        uiState = uiState,
        onNavigate = onNavigate
    )
}

@Composable
fun BadgesScreen(
    uiState: BadgesUiState,
    onNavigate: (AppScreen) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BadgeBackground()
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { BadgesTopBar() }
        ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                contentPadding = PaddingValues(bottom = 96.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    TotalBadgesHeader(
                        collected = uiState.collected,
                        total = uiState.total
                    )
                }
                item(span = { GridItemSpan(2) }) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(uiState.badges) { badge ->
                    BadgeCard(badge = badge)
                }
            }
        }
    }
}

@Composable
private fun BadgesTopBar() {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .shadow(6.dp, CircleShape)
                        .background(Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .background(BluePrimary.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "\uD83D\uDC7D", fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Explorador de Emoções",
                    style = MaterialTheme.typography.titleLarge,
                    color = BluePrimary,
                    fontWeight = FontWeight.Black
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = null,
                    tint = TextSecondaryLight
                )
            }
        }
    }
}

@Composable
private fun TotalBadgesHeader(
    collected: Int,
    total: Int,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(BluePrimary.copy(alpha = 0.1f))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = "PROGRESSO DA COLEÇÃO",
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.4.sp,
                color = BluePrimary
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Total de medalhas",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = String.format("%02d", collected),
                fontSize = 44.sp,
                fontWeight = FontWeight.Black,
                color = BluePrimary
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "COLETADAS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    color = TextSecondaryLight
                )
                Text(
                    text = "/ $total",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = TextSecondaryLight.copy(alpha = 0.6f)
                )
            }
        }
    }
}

private fun previewBadges(): List<BadgeUiModel> = listOf(
    BadgeUiModel(
        title = "Primeira sessão",
        subtitle = "Conclua 1 sessão",
        icon = Icons.Rounded.Mood,
        tint = AccentAmber,
        status = BadgeStatus.Earned
    ),
    BadgeUiModel(
        title = "Trinca focada",
        subtitle = "Conclua 3 sessões",
        icon = Icons.Rounded.GraphicEq,
        tint = AccentBlue,
        status = BadgeStatus.InProgress,
        progressLabel = "2/3",
        progress = 0.66f
    ),
    BadgeUiModel(
        title = "Cinco jornadas",
        subtitle = "Conclua 5 sessões",
        icon = Icons.Rounded.Psychology,
        tint = BluePrimary,
        status = BadgeStatus.Locked
    ),
    BadgeUiModel(
        title = "Ritmo de 10",
        subtitle = "Conclua 10 sessões",
        icon = Icons.Rounded.WbSunny,
        tint = AccentPurple,
        status = BadgeStatus.Locked
    )
)

@Composable
private fun BadgeCard(badge: BadgeUiModel) {
    val muted = badge.status != BadgeStatus.Earned
    val overallAlpha = if (badge.status == BadgeStatus.Locked) 0.5f else 1f
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .alpha(overallAlpha)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (badge.status == BadgeStatus.Earned) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                        .background(badge.tint.copy(alpha = 0.16f))
                        .blur(28.dp)
                )
            }
            BadgeCircle(badge = badge)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = badge.title.uppercase(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp,
            color = if (muted) TextSecondaryLight.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        badge.subtitle?.let { subtitle ->
            Text(
                text = subtitle,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondaryLight.copy(alpha = if (muted) 0.5f else 0.8f),
                textAlign = TextAlign.Center
            )
        }
        if (badge.status == BadgeStatus.InProgress) {
            Spacer(modifier = Modifier.height(8.dp))
            ProgressPill(progress = badge.progress)
        }
    }
}

@Composable
private fun BadgeCircle(badge: BadgeUiModel) {
    val baseSize = 120.dp
    val iconTint = when (badge.status) {
        BadgeStatus.Earned -> badge.tint
        BadgeStatus.InProgress -> TextSecondaryLight.copy(alpha = 0.7f)
        BadgeStatus.Locked -> TextSecondaryLight.copy(alpha = 0.4f)
    }

    Box(
        modifier = Modifier
            .size(baseSize)
            .then(if (badge.status != BadgeStatus.Locked) Modifier.shadow(8.dp, CircleShape, clip = false) else Modifier)
            .then(
                if (badge.status == BadgeStatus.Locked) {
                    Modifier.dashedCircleBorder(
                        color = TextSecondaryLight.copy(alpha = 0.35f),
                        strokeWidth = 2.dp
                    )
                } else {
                    Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .borderCircle(
                            color = if (badge.status == BadgeStatus.Earned) badge.tint else Color(0xFFE2E8F0),
                            strokeWidth = 6.dp
                        )
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (badge.status == BadgeStatus.Locked) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(Color(0xFFF8FAFC))
            )
        }
        if (badge.status == BadgeStatus.InProgress) {
            BadgeProgressRing(progress = badge.progress)
        }
        Icon(
            imageVector = badge.icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(44.dp)
        )
        if (badge.status == BadgeStatus.Earned) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(4.dp, (-4).dp)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(badge.tint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        if (badge.status == BadgeStatus.InProgress) {
            badge.progressLabel?.let { label ->
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 6.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = TextSecondaryLight
                    )
                }
            }
        }
        if (badge.status == BadgeStatus.Locked) {
            Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = null,
                tint = TextSecondaryLight.copy(alpha = 0.45f),
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@Composable
private fun BadgeProgressRing(progress: Float) {
    Box(
        modifier = Modifier
            //.matchParentSize()
            .drawBehind {
                drawArc(
                    color = BluePrimary.copy(alpha = 0.2f),
                    startAngle = -90f,
                    sweepAngle = 360 * progress,
                    useCenter = false,
                    style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
                )
            }
    )
}

@Composable
private fun ProgressPill(progress: Float) {
    Box(
        modifier = Modifier
            .width(64.dp)
            .height(6.dp)
            .clip(CircleShape)
            .background(Color(0xFFE2E8F0))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .clip(CircleShape)
                .background(TextSecondaryLight.copy(alpha = 0.5f))
        )
    }
}

@Composable
private fun BadgeBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.TopEnd)
                .offset(x = 120.dp, y = (-120).dp)
                .clip(CircleShape)
                .background(AccentBlue.copy(alpha = 0.15f))
                .blur(120.dp)
        )
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-120).dp, y = 120.dp)
                .clip(CircleShape)
                .background(AccentAmber.copy(alpha = 0.18f))
                .blur(120.dp)
        )
        Box(
            modifier = Modifier
                .size(420.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(Color(0xFFF8FAFC))
                .blur(140.dp)
        )
    }
}

private fun Modifier.dashedCircleBorder(
    color: Color,
    strokeWidth: Dp,
): Modifier = drawWithContent {
    drawContent()
    drawCircle(
        color = color,
        style = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f))
        )
    )
}

private fun Modifier.borderCircle(
    color: Color,
    strokeWidth: Dp,
): Modifier = drawWithContent {
    drawContent()
    drawCircle(
        color = color,
        style = Stroke(width = strokeWidth.toPx())
    )
}

@Preview(showBackground = true)
@Composable
private fun BadgesScreenPreview() {
    EmotionTheme {
        BadgesScreen(
            uiState = BadgesUiState(
                collected = 1,
                total = 6,
                badges = previewBadges()
            ),
            onNavigate = {}
        )
    }
}
