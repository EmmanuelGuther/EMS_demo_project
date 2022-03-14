package com.emmanuelguther.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.emmanuelguther.core_presentation.ui.theme.Teal

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

@Composable
fun CircularIcon(modifier: Modifier, imageVector: ImageVector, contentDescription: String) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .background(Teal)
            .padding(4.dp)
            .size(28.dp),
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = Color.White
    )
}

@Composable
fun FullScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )

    }
}

@Composable
fun ErrorAlert(errorMessage: String, buttonText: String, action: () -> Unit) {
        val snackBarVisibleState = remember { mutableStateOf(true) }
      Box(){
          if (snackBarVisibleState.value) {
              Snackbar(
                  action = {
                      Button(onClick = {
                          action.invoke()
                          snackBarVisibleState.value = false
                      }
                      ) { Text(buttonText) }
                  },
                  modifier = Modifier.padding(8.dp)
              ) { Text(text = errorMessage) }
          }
      }


}