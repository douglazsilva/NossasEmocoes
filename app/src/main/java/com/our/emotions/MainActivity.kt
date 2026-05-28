package com.our.emotions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.our.emotions.ui.navigation.EmotionExplorerApp
import com.our.emotions.ui.theme.EmotionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmotionTheme {
                EmotionExplorerApp()
            }
        }
    }
}
