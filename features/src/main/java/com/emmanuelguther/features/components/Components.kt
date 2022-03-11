package com.emmanuelguther.features.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun DSButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    androidx.compose.material.Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = CutCornerShape(10)
    ) {
        Text(text = text)
    }
}

@Composable
fun LoadingText(text: String, modifier: Modifier) {
    val textScale by rememberInfiniteTransition().animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        )
    )
    Text(
        modifier = modifier
            .padding(16.dp, 80.dp)
            .scale(textScale),
        text = text,
        color = MaterialTheme.colors.secondaryVariant
    )
}