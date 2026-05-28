package com.our.emotions.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.our.emotions.R
import com.our.emotions.domain.model.EmotionOption
import com.our.emotions.ui.components.GameplayTopBar
import com.our.emotions.ui.navigation.AppScreen
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.EmotionTheme
import com.our.emotions.ui.theme.TextSecondaryLight
import com.our.emotions.ui.viewmodel.GameplayUiState
import com.our.emotions.ui.viewmodel.GameplayViewModel
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.Locale

@Composable
fun GameplayRoute(
    onNavigate: (AppScreen) -> Unit,
    viewModel: GameplayViewModel = viewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.loadQuestion()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GameplayScreen(
        uiState = uiState,
        onOptionSelected = { option ->
            viewModel.onOptionSelected(option)
            onNavigate(AppScreen.Feedback)
        },
        onNavigate = onNavigate,
    )
}

@Composable
fun GameplayScreen(
    uiState: GameplayUiState,
    onOptionSelected: (EmotionOption) -> Unit,
    onNavigate: (AppScreen) -> Unit,
) {
    val context = LocalContext.current
    val isInPreview = LocalInspectionMode.current
    var textToSpeech by remember { mutableStateOf<Any?>(null) }
    var isTtsReady by remember { mutableStateOf(false) }
    var pendingSpeakRequest by remember { mutableStateOf(false) }
    var queueFlush by remember { mutableStateOf(0) }
    val promptToSpeak = "Você consegue encontrar alguém que esteja ${uiState.prompt}"

    DisposableEffect(context, isInPreview) {
        if (isInPreview) {
            onDispose { }
        } else {
            var localTts: Any? = null
            try {
                val ttsClass = Class.forName("android.speech.tts.TextToSpeech")
                val onInitListenerClass =
                    Class.forName("android.speech.tts.TextToSpeech\$OnInitListener")
                val successStatus = ttsClass.getField("SUCCESS").getInt(null)
                val onInitListener = Proxy.newProxyInstance(
                    onInitListenerClass.classLoader ?: ClassLoader.getSystemClassLoader(),
                    arrayOf(onInitListenerClass)
                ) { _, method, args ->
                    if (method.name == "onInit") {
                        val status = args?.firstOrNull() as? Int ?: -1
                        if (status == successStatus && localTts != null) {
                            localTts!!.javaClass
                                .getMethod("setLanguage", Locale::class.java)
                                .invoke(localTts, Locale("pt", "BR"))
                            isTtsReady = true
                            if (pendingSpeakRequest) {
                                invokeTtsSpeak(
                                    ttsEngine = localTts!!,
                                    text = promptToSpeak,
                                    queueMode = queueFlush
                                )
                                pendingSpeakRequest = false
                            }
                        } else {
                            isTtsReady = false
                        }
                    }
                    null
                }

                localTts = ttsClass
                    .getConstructor(android.content.Context::class.java, onInitListenerClass)
                    .newInstance(context, onInitListener)
                queueFlush = ttsClass.getField("QUEUE_FLUSH").getInt(null)
                textToSpeech = localTts
            } catch (_: Throwable) {
                isTtsReady = false
                textToSpeech = null
            }
            onDispose {
                try {
                    textToSpeech?.javaClass?.getMethod("stop")?.invoke(textToSpeech)
                } catch (_: Throwable) {
                }
                try {
                    textToSpeech?.javaClass?.getMethod("shutdown")?.invoke(textToSpeech)
                } catch (_: Throwable) {
                }
                textToSpeech = null
                isTtsReady = false
                pendingSpeakRequest = false
            }
        }
    }

    fun speakPrompt() {
        if (isInPreview) return
        if (!isTtsReady) {
            pendingSpeakRequest = true
            return
        }
        try {
            val ttsEngine = textToSpeech ?: return
            invokeTtsSpeak(
                ttsEngine = ttsEngine,
                text = promptToSpeak,
                queueMode = queueFlush
            )
        } catch (_: Throwable) {
        }
    }

    val progress = if (uiState.totalQuestions > 0) {
        uiState.questionNumber.toFloat() / uiState.totalQuestions
    } else {
        0f
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            GameplayTopBar(
                levelLabel = uiState.levelLabel,
                progressLabel = "${uiState.questionNumber} / ${uiState.totalQuestions}",
                progress = progress,
                onBack = { onNavigate(AppScreen.LevelSelection) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            AudioPrompt(
                prompt = uiState.prompt,
                onSpeakPrompt = { speakPrompt() }
            )
            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(uiState.options) { option ->
                    EmotionPhotoCard(
                        option = option,
                        onClick = { onOptionSelected(option) }
                    )
                }
            }
        }
    }
}

private fun invokeTtsSpeak(
    ttsEngine: Any,
    text: String,
    queueMode: Int,
) {
    val modernSpeakMethod = findMethod(
        targetClass = ttsEngine.javaClass,
        name = "speak",
        parameterTypes = arrayOf(
            String::class.java,
            Int::class.javaPrimitiveType ?: Int::class.java,
            android.os.Bundle::class.java,
            String::class.java
        )
    )
    if (modernSpeakMethod != null) {
        modernSpeakMethod.invoke(ttsEngine, text, queueMode, null, "gameplay_prompt")
        return
    }

    val legacySpeakMethod = findMethod(
        targetClass = ttsEngine.javaClass,
        name = "speak",
        parameterTypes = arrayOf(
            String::class.java,
            Int::class.javaPrimitiveType ?: Int::class.java,
            HashMap::class.java
        )
    )
    legacySpeakMethod?.invoke(ttsEngine, text, queueMode, null)
}

private fun findMethod(
    targetClass: Class<*>,
    name: String,
    parameterTypes: Array<Class<*>>
): Method? {
    return try {
        targetClass.getMethod(name, *parameterTypes)
    } catch (_: Throwable) {
        null
    }
}

@Composable
private fun AudioPrompt(
    prompt: String,
    onSpeakPrompt: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(BluePrimary)
                .clickable { onSpeakPrompt() }
                .border(6.dp, BluePrimary.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Você consegue encontrar alguém que esteja",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = prompt,
            style = MaterialTheme.typography.headlineLarge,
            color = BluePrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Toque na foto correta abaixo",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryLight
        )
    }
}

@Composable
private fun EmotionPhotoCard(
    option: EmotionOption,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Image(
            painter = painterResource(id = option.imageResId),
            contentDescription = "Foto de alguém ${option.label}",
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GameplayScreenPreview() {
    EmotionTheme {
        GameplayScreen(
            uiState = GameplayUiState(
                levelLabel = "NÍVEL 1",
                prompt = "surpreso",
                options = listOf(
                    EmotionOption("Feliz", R.drawable.emo_feliz_1),
                    EmotionOption("Triste", R.drawable.emo_triste_8),
                ),
                questionNumber = 1,
                totalQuestions = 3
            ),
            onOptionSelected = {},
            onNavigate = {}
        )
    }
}
