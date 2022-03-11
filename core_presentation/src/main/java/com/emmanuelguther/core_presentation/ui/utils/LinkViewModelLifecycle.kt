package com.emmanuelguther.core_presentation.ui.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun <C, E, Event : UiEvent, Effect : UiEffect> LinkViewModelLifecycle(viewModel: MViewModel<C, E, Event, Effect>) {
    val activity = LocalContext.current as ComponentActivity
    val lifecycle = activity.lifecycle
    DisposableEffect(Unit) {
        lifecycle.addObserver(viewModel)
        onDispose {
            lifecycle.removeObserver(viewModel)
        }
    }
}