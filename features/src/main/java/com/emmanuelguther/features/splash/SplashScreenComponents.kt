package com.emmanuelguther.features.splash

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emmanuelguther.core_presentation.ui.utils.LinkViewModelLifecycle
import com.emmanuelguther.core_presentation.ui.utils.ViewModelGenericError
import com.emmanuelguther.core_presentation.ui.utils.ViewModelState
import com.emmanuelguther.features.R
import com.emmanuelguther.features.components.DSButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
private fun Content(
    state: ViewModelState<out SplashViewModel.State, ViewModelGenericError>,
    viewModel: SplashViewModel
) {
    Surface(Modifier.fillMaxSize()) {
        when {
            state.loading() -> Log.i("LOADING", "LOADING")
            state is ViewModelState.Loaded -> {
                RenderGreeting(state.content.username) { viewModel.setEvent(SplashViewModel.Event.OnButtonPressed) }
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
private fun RenderGreeting(username: String = "", onButtonClicked: () -> Unit) {
    val usernameState = remember { mutableStateOf(username) }
    val columnModifier = Modifier
        .padding(80.dp)
        .fillMaxWidth()
        .height(100.dp)
        .background(MaterialTheme.colors.primary)
        .shadow(10.dp)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = columnModifier
        ) {
            Text(text = stringResource(R.string.welcome))
            Text(text = usernameState.value)
        }

        DSButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.continue_)
        ) {
            onButtonClicked()
        }
    }
}