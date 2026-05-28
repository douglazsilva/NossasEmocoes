package com.our.emotions.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.our.emotions.ui.components.AppTopBar
import com.our.emotions.ui.navigation.AppScreen
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.AccentGreen
import com.our.emotions.ui.theme.AccentPurple
import com.our.emotions.ui.theme.AccentRed
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.EmotionTheme
import com.our.emotions.ui.theme.OutlineLight
import com.our.emotions.ui.theme.SurfaceLight
import com.our.emotions.ui.theme.TextPrimaryLight
import com.our.emotions.ui.theme.TextSecondaryLight

@Composable
fun ProfileRoute(
    onNavigate: (AppScreen) -> Unit,
) {
    ProfileScreen(onNavigate = onNavigate)
}

@Composable
fun ProfileScreen(
    onNavigate: (AppScreen) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { ProfileTopBar() }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                AccentBlue.copy(alpha = 0.08f),
                                AccentPurple.copy(alpha = 0.05f),
                                Color.Transparent
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                ProfileHeader()
                StatsSection()
                CustomizationSection()
                SettingsSection()
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ProfileTopBar() {
    AppTopBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .shadow(4.dp, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(BluePrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            tint = BluePrimary
                        )
                    }
                }
                Text(
                    text = "EXPLORADOR DE EMOÇÕES",
                    style = MaterialTheme.typography.labelLarge,
                    color = BluePrimary,
                    letterSpacing = 1.4.sp
                )
            }
        },
        showSettings = true
    )
}

@Composable
private fun ProfileHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .shadow(8.dp, CircleShape)
                    .border(BorderStroke(3.dp, BluePrimary), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "\uD83E\uDDD4", fontSize = 36.sp)
            }
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(BluePrimary)
                    .border(BorderStroke(2.dp, Color.White), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        Text(
            text = "Explorador Sam",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimaryLight
        )
        Text(
            text = "NÍVEL 14 DESBRAVADOR",
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondaryLight
        )
        Button(
            onClick = {},
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp, vertical = 6.dp)
        ) {
            Text(text = "Editar perfil", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun StatsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "ESTATÍSTICAS",
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondaryLight
        )
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = SurfaceLight),
            border = BorderStroke(2.dp, OutlineLight),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "124",
                        style = MaterialTheme.typography.headlineLarge,
                        color = BluePrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "XP TOTAL GANHO",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondaryLight
                    )
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(AccentBlue.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Mic,
                        contentDescription = null,
                        tint = AccentBlue
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatSmallCard(title = "12", label = "DIAS ATIVOS", accent = AccentAmber)
            StatSmallCard(title = "42", label = "EMOÇÕES ENCONTRADAS", accent = AccentBlue)
        }
    }
}

@Composable
private fun StatSmallCard(title: String, label: String, accent: Color) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = SurfaceLight),
        border = BorderStroke(2.dp, OutlineLight),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = accent,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondaryLight
            )
        }
    }
}

@Composable
private fun CustomizationSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "KIT DE PERSONALIZAÇÃO",
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondaryLight
            )
            Text(
                text = "VER LOJA",
                style = MaterialTheme.typography.labelLarge,
                color = BluePrimary
            )
        }
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = SurfaceLight),
            border = BorderStroke(2.dp, OutlineLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "CHAPÉUS CONQUISTADOS",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondaryLight
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        RewardBadge(active = true, iconColor = BluePrimary)
                        RewardBadge(active = false, iconColor = TextSecondaryLight)
                        RewardBadge(active = false, iconColor = TextSecondaryLight)
                        RewardBadge(active = false, iconColor = TextSecondaryLight)
                        RewardBadge(active = false, iconColor = TextSecondaryLight)
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "FUNDO",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextSecondaryLight
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ColorDot(AccentBlue)
                        ColorDot(AccentAmber)
                        ColorDot(AccentRed)
                        ColorDot(AccentGreen)
                        ColorDot(Color(0xFF1F2937))
                    }
                }
            }
        }
    }
}

@Composable
private fun RewardBadge(active: Boolean, iconColor: Color) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(
                BorderStroke(2.dp, if (active) BluePrimary else OutlineLight),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.Person,
            contentDescription = null,
            tint = iconColor
        )
    }
}

@Composable
private fun ColorDot(color: Color) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(color)
            .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.7f)), CircleShape)
    )
}

@Composable
private fun SettingsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "CONFIGURAÇÕES",
            style = MaterialTheme.typography.labelLarge,
            color = TextSecondaryLight
        )
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = SurfaceLight),
            border = BorderStroke(2.dp, OutlineLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SettingsRow(
                    icon = Icons.Rounded.Notifications,
                    label = "Notificações",
                    checked = true,
                    accent = BluePrimary
                )
                SettingsRow(
                    icon = Icons.AutoMirrored.Rounded.VolumeUp,
                    label = "Efeitos sonoros",
                    checked = false,
                    accent = TextSecondaryLight
                )
                LogoutRow()
            }
        }
    }
}

@Composable
private fun SettingsRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    checked: Boolean,
    accent: Color,
) {
    val isChecked = remember { mutableStateOf(checked) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = accent)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = label, style = MaterialTheme.typography.titleMedium, color = TextPrimaryLight)
        }
        Switch(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = BluePrimary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = OutlineLight
            )
        )
    }
}

@Composable
private fun LogoutRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(AccentRed.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Logout,
                    contentDescription = null,
                    tint = AccentRed
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Sair", style = MaterialTheme.typography.titleMedium, color = AccentRed)
        }
        Text(
            text = "\u203A",
            color = TextSecondaryLight,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    EmotionTheme {
        ProfileScreen(onNavigate = {})
    }
}
