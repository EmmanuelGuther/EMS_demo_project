package com.emmanuelguther.features.splash

import androidx.lifecycle.viewModelScope
import com.emmanuelguther.core_presentation.*
import com.emmanuelguther.core_presentation.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SplashViewModel @Inject constructor() :
    MViewModel<SplashViewModel.State, ViewModelGenericError, SplashViewModel.Event, SplashViewModel.Effect>() {

    init {
        loadUser()
    }

    fun loadUser() = viewModelScope.launch {}


    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnButtonPressed -> {
                setEffect { Effect.NavigateToMain }
            }
        }
    }

    data class State(val username: String)

    sealed class Event : UiEvent {
        object OnButtonPressed : Event()
    }

    sealed class Effect : UiEffect {
        object NavigateToMain : Effect()
    }
}