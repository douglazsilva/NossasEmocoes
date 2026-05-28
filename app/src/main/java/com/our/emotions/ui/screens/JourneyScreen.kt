package com.our.emotions.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Landscape
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Park
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Waves
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.our.emotions.ui.components.AppTopBar
import com.our.emotions.data.repository.LevelProgressStore
import com.our.emotions.data.repository.LevelRepository
import com.our.emotions.domain.model.EmotionLevel
import com.our.emotions.domain.model.LevelStatus
import com.our.emotions.ui.navigation.AppScreen
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.EmotionTheme
import com.our.emotions.ui.theme.TextSecondaryLight

@Composable
fun JourneyRoute(
    onNavigate: (AppScreen) -> Unit,
) {
    JourneyScreen(onNavigate = onNavigate)
}

@Composable
fun JourneyScreen(
    onNavigate: (AppScreen) -> Unit,
) {
    val completedCount = LevelProgressStore.completedCount()
    val levels = LevelRepository().loadLevels(completedCount = completedCount)
    val forestLevels = levels.filter { it.level in 1..4 }
    val caveLevels = levels.filter { it.level in 5..6 }
    val forestProgress = regionProgress(forestLevels)
    val caveProgress = regionProgress(caveLevels)
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { JourneyTopBar() }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            JourneyBackgroundBlobs()
            JourneyDecorIcons()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ForestRegionSection(
                    levels = forestLevels,
                    progress = forestProgress,
                    onNavigate = onNavigate
                )
                CaveRegionSection(
                    levels = caveLevels,
                    progress = caveProgress,
                    onNavigate = onNavigate
                )
                SeaRegionSection(
                    unlocked = completedCount >= 6,
                    onNavigate = onNavigate
                )
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun JourneyTopBar() {
    AppTopBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(4.dp, BluePrimary, CircleShape)
                        .padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "\uD83D\uDC66", fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "EXPLORADOR DE EMOÇÕES",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    color = BluePrimary
                )
            }
        }
    )
}

@Composable
private fun JourneyBackgroundBlobs() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = (-120).dp, y = (-120).dp)
                .background(AccentAmber.copy(alpha = 0.35f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = 220.dp, y = 280.dp)
                .background(AccentBlue.copy(alpha = 0.25f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(240.dp)
                .offset(x = (-80).dp, y = 680.dp)
                .background(Color(0xFFF4B9B2).copy(alpha = 0.3f), CircleShape)
        )
    }
}

@Composable
private fun JourneyDecorIcons() {
    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.material3.Icon(
            imageVector = Icons.Rounded.Park,
            contentDescription = null,
            tint = AccentAmber.copy(alpha = 0.2f),
            modifier = Modifier
                .size(64.dp)
                .offset(x = 24.dp, y = 120.dp)
        )
        androidx.compose.material3.Icon(
            imageVector = Icons.Rounded.Landscape,
            contentDescription = null,
            tint = Color(0xFF1F2937).copy(alpha = 0.2f),
            modifier = Modifier
                .size(64.dp)
                .offset(x = 280.dp, y = 540.dp)
        )
        androidx.compose.material3.Icon(
            imageVector = Icons.Rounded.Waves,
            contentDescription = null,
            tint = BluePrimary.copy(alpha = 0.2f),
            modifier = Modifier
                .size(64.dp)
                .offset(x = 36.dp, y = 880.dp)
        )
    }
}

@Composable
private fun ForestRegionSection(
    levels: List<EmotionLevel>,
    progress: Float,
    onNavigate: (AppScreen) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RegionHeader(
            regionLabel = "REGIÃO 01",
            title = "Floresta da Alegria",
            titleColor = AccentAmber,
            underlineColor = AccentAmber.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(520.dp)
        ) {
            ForestPath(modifier = Modifier.matchParentSize(), progress = progress)
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val byLevel = levels.associateBy { it.level }
                LevelNodeForStatus(
                    level = byLevel[1],
                    modifier = Modifier.offset(y = -20.dp),
                    onNavigate = { onNavigate(AppScreen.LevelSelection) }
                )
                LevelNodeForStatus(
                    level = byLevel[2],
                    modifier = Modifier.offset(x = 92.dp, y = -42.dp),
                    onNavigate = { onNavigate(AppScreen.LevelSelection) }
                )
                LevelNodeForStatus(
                    level = byLevel[3],
                    modifier = Modifier.offset(x = (-96).dp, y = -24.dp),
                    onNavigate = { onNavigate(AppScreen.LevelSelection) }
                )
                LevelNodeForStatus(
                    level = byLevel[4],
                    modifier = Modifier.offset(y = 10.dp),
                    onNavigate = { onNavigate(AppScreen.LevelSelection) }
                )
            }
        }
    }
}

@Composable
private fun CaveRegionSection(
    levels: List<EmotionLevel>,
    progress: Float,
    onNavigate: (AppScreen) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RegionHeader(
            regionLabel = "REGIÃO 02",
            title = "Caverna do Medo",
            titleColor = MaterialTheme.colorScheme.onBackground,
            underlineColor = Color(0xFFE2E8F0)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
        ) {
            CavePath(modifier = Modifier.matchParentSize(), progress = progress)
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val byLevel = levels.associateBy { it.level }
                LevelNodeForStatus(
                    level = byLevel[5],
                    modifier = Modifier.offset(x = (-28).dp),
                    onNavigate = { onNavigate(AppScreen.LevelSelection) }
                )
                LevelNodeForStatus(
                    level = byLevel[6],
                    modifier = Modifier.offset(x = 28.dp),
                    onNavigate = { onNavigate(AppScreen.LevelSelection) }
                )
            }
        }
    }
}

@Composable
private fun SeaRegionSection(
    unlocked: Boolean,
    onNavigate: (AppScreen) -> Unit,
) {
    val alpha = if (unlocked) 1f else 0.5f
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.alpha(alpha)) {
        RegionHeader(
            regionLabel = "REGIÃO 03",
            title = "Mar da Calma",
            titleColor = BluePrimary,
            underlineColor = Color.Transparent
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (unlocked) {
            CurrentLevelNode(
                label = "ATUAL: NÍVEL 7",
                onClick = { onNavigate(AppScreen.LevelSelection) }
            )
        } else {
            LockedLevelPreviewNode()
        }
    }
}

@Composable
private fun RegionHeader(
    regionLabel: String,
    title: String,
    titleColor: Color,
    underlineColor: Color,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = regionLabel,
            color = TextSecondaryLight,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
            color = titleColor
        )
        if (underlineColor != Color.Transparent) {
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .size(width = 48.dp, height = 6.dp)
                    .clip(CircleShape)
                    .background(underlineColor)
            )
        }
    }
}

@Composable
private fun CompletedLevelNode(
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(8.dp, CircleShape)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .border(4.dp, BluePrimary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = null,
                tint = BluePrimary,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LevelPill(
            label = label,
            background = BluePrimary.copy(alpha = 0.15f),
            textColor = BluePrimary
        )
    }
}

@Composable
private fun CurrentLevelNode(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(BluePrimary.copy(alpha = 0.12f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .shadow(12.dp, CircleShape)
                    .background(BluePrimary, CircleShape)
                    .border(4.dp, BluePrimary, CircleShape)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LevelPill(
            label = label,
            background = BluePrimary,
            textColor = Color.White,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun LockedLevelNode(
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(4.dp, CircleShape)
                .background(Color(0xFFF1F5F9), CircleShape)
                .border(4.dp, Color(0xFFE2E8F0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = null,
                tint = TextSecondaryLight,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = TextSecondaryLight,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )
    }
}

@Composable
private fun LockedLevelPreviewNode(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(4.dp, CircleShape)
                .background(Color(0xFFF1F5F9), CircleShape)
                .border(4.dp, Color(0xFFE2E8F0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = null,
                tint = TextSecondaryLight,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun LevelPill(
    label: String,
    background: Color,
    textColor: Color,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontWeight = fontWeight,
            fontSize = 10.sp,
            letterSpacing = 1.sp
        )
    }
}

@Composable
private fun ForestPath(
    modifier: Modifier = Modifier,
    progress: Float,
) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val scaleX = w / 200f
        val scaleY = h / 500f
        fun x(v: Float) = v * scaleX
        fun y(v: Float) = v * scaleY
        val path = Path().apply {
            moveTo(x(100f), y(0f))
            cubicTo(x(100f), y(50f), x(160f), y(80f), x(160f), y(150f))
            cubicTo(x(160f), y(220f), x(40f), y(250f), x(40f), y(320f))
            cubicTo(x(40f), y(390f), x(100f), y(420f), x(100f), y(500f))
        }
        drawPath(
            path = path,
            color = Color(0xFFCBD5E1),
            style = Stroke(
                width = 4.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f))
            )
        )
        val completedPath = Path()
        val measure = PathMeasure()
        val clampedProgress = progress.coerceIn(0f, 1f)
        measure.setPath(path, false)
        val length = measure.length
        measure.getSegment(0f, length * clampedProgress, completedPath, true)
        drawPath(
            path = completedPath,
            color = BluePrimary,
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun CavePath(
    modifier: Modifier = Modifier,
    progress: Float,
) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val scaleX = w / 200f
        val scaleY = h / 500f
        fun x(v: Float) = v * scaleX
        fun y(v: Float) = v * scaleY
        val path = Path().apply {
            moveTo(x(100f), y(0f))
            cubicTo(x(100f), y(50f), x(40f), y(80f), x(40f), y(150f))
            cubicTo(x(40f), y(220f), x(160f), y(250f), x(160f), y(320f))
            cubicTo(x(160f), y(390f), x(100f), y(420f), x(100f), y(500f))
        }
        drawPath(
            path = path,
            color = Color(0xFFCBD5E1),
            style = Stroke(
                width = 4.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f))
            )
        )
        val completedPath = Path()
        val measure = PathMeasure()
        val clampedProgress = progress.coerceIn(0f, 1f)
        measure.setPath(path, false)
        val length = measure.length
        measure.getSegment(0f, length * clampedProgress, completedPath, true)
        drawPath(
            path = completedPath,
            color = BluePrimary,
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun LevelNodeForStatus(
    level: EmotionLevel?,
    modifier: Modifier,
    onNavigate: () -> Unit,
) {
    if (level == null) {
        LockedLevelNode(label = "NÍVEL ?", modifier = modifier)
        return
    }
    when (level.status) {
        LevelStatus.Completed -> CompletedLevelNode(
            label = "NÍVEL ${level.level}",
            modifier = modifier
        )
        LevelStatus.PlayNow -> CurrentLevelNode(
            label = "ATUAL: NÍVEL ${level.level}",
            modifier = modifier,
            onClick = onNavigate
        )
        else -> LockedLevelNode(
            label = "NÍVEL ${level.level}",
            modifier = modifier
        )
    }
}

private fun regionProgress(levels: List<EmotionLevel>): Float {
    if (levels.isEmpty()) return 0f
    val completed = levels.count { it.status == LevelStatus.Completed }
    return completed.toFloat() / levels.size.toFloat()
}

@Preview(showBackground = true)
@Composable
private fun JourneyScreenPreview() {
    EmotionTheme {
        JourneyScreen(
            onNavigate = {},
        )
    }
}
