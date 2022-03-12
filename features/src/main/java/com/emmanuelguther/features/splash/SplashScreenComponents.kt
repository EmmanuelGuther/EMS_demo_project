package com.emmanuelguther.features.splash

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.emmanuelguther.core_presentation.ui.theme.Teal
import com.emmanuelguther.core_presentation.ui.utils.LinkViewModelLifecycle
import com.emmanuelguther.core_presentation.ui.utils.ViewModelGenericError
import com.emmanuelguther.core_presentation.ui.utils.ViewModelState
import com.emmanuelguther.features.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun SplashScreen(viewModel: SplashViewModel, onNavigateToMain: () -> Unit) {
    LinkViewModelLifecycle(viewModel)

    val state by viewModel.state.collectAsState(initial = ViewModelState.initialState)
    Content(state, viewModel)

    Effects(viewModel, onNavigateToMain)
}

@ExperimentalCoroutinesApi
@Composable
private fun Content(state: ViewModelState<out SplashViewModel.State, ViewModelGenericError>, viewModel: SplashViewModel) {
    Surface(Modifier.fillMaxSize()) {
        when {
            state.loading() -> Log.i("LOADING", "LOADING")
            state is ViewModelState.Loaded -> {
                RenderCharger(state.content.username) { viewModel.setEvent(SplashViewModel.Event.OnButtonPressed) }
            }
            state is ViewModelState.Error -> Log.e("ERROR", "ERROR")
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun Effects(viewModel: SplashViewModel, onNavigateToMain: () -> Unit) {
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { value ->
            when (value) {
                SplashViewModel.Effect.NavigateToMain -> onNavigateToMain()
            }
        }
    }
}

@Composable
private fun RenderCharger(username: String = "", onFinishSplash: () -> Unit) {
    val usernameState = remember { mutableStateOf(username) }
    val logoVisibility = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp, 64.dp),
            text = """${stringResource(id = R.string.welcome)} ${usernameState.value}""",
            color = Teal
        )
        AnimatedVisibility(visible = logoVisibility.value) {
            LogoAnimation(Modifier.align(Alignment.Center))
        }

    }

    LaunchedEffect(key1 = usernameState) {
        if (usernameState.value.isNotEmpty()) {
            delay(2500)
            //onFinishSplash()
        }
    }
}

@Composable
fun LogoAnimation(modifier: Modifier) {
    var isAnimated by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isAnimated, label = "transition")
    val imageSize by transition.animateDp(transitionSpec = { tween(1000) }, "") { animated ->
        if (animated) 150.dp else 0.dp
    }
    val circleScale by rememberInfiniteTransition().animateFloat(
        initialValue = 0.2f,
        targetValue = 5.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500),
            repeatMode = RepeatMode.Reverse
        )
    )
    val chargerScale by rememberInfiniteTransition().animateFloat(
        initialValue = 1.0f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(key1 = Unit) { isAnimated = !isAnimated }

    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.main_animated_view_circle_with_shadow),
            contentDescription = "Circle",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .zIndex(0f)
                .scale(circleScale)
                .padding(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp
                )
                .align(Alignment.Center)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_w_charger),
            contentDescription = "Charger",
            modifier = modifier
                .size(imageSize)
                .scale(chargerScale)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_w_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .size(imageSize)
        )
    }
}


@Preview(showBackground = true, device = Devices.NEXUS_5, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RenderCharger("") { }
}