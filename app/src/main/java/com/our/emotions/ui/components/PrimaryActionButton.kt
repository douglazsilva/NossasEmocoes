package com.our.emotions.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.our.emotions.ui.theme.BluePrimary

@Composable
fun PrimaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 56.dp,
    shape: Shape = MaterialTheme.shapes.large,
    showShadow: Boolean = false,
    trailingIcon: ImageVector? = null,
) {
    val baseModifier = if (showShadow) {
        modifier
            .fillMaxWidth()
            .height(height)
            .shadow(8.dp, shape)
    } else {
        modifier
            .fillMaxWidth()
            .height(height)
    }

    Button(
        onClick = onClick,
        modifier = baseModifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = BluePrimary,
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        if (trailingIcon != null) {
            Spacer(modifier = Modifier.width(8.dp))
            androidx.compose.material3.Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
