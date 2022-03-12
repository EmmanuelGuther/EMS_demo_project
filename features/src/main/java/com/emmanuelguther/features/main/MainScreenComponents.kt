package com.emmanuelguther.features.main

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.emmanuelguther.core_presentation.ui.utils.LinkViewModelLifecycle
import com.emmanuelguther.core_presentation.ui.utils.ViewModelGenericError
import com.emmanuelguther.core_presentation.ui.utils.ViewModelState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun MainScreen(viewModel: MainViewModel, onNavigateToDetail: () -> Unit) {
    LinkViewModelLifecycle(viewModel)

    val state by viewModel.state.collectAsState(initial = ViewModelState.initialState)
    Content(state, viewModel)

    Effects(viewModel, onNavigateToDetail)
}

@ExperimentalCoroutinesApi
@Composable
private fun Content(state: ViewModelState<out MainViewModel.State, ViewModelGenericError>, viewModel: MainViewModel) {
    Surface(Modifier.fillMaxSize()) {
        when {
            state.loading() -> Log.i("LOADING", "LOADING")
            state is ViewModelState.Loaded -> {}
            state is ViewModelState.Error -> Log.e("ERROR", "ERROR")
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
private fun Effects(viewModel: MainViewModel, onNavigateToMain: () -> Unit) {
    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { value ->
            when (value) {
                MainViewModel.Effect.NavigateToDetail -> onNavigateToMain()
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.NEXUS_5, showSystemUi = true)
@Composable
fun DefaultPreview() {
}