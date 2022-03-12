package com.emmanuelguther.features.main

import androidx.lifecycle.viewModelScope
import com.emmanuelguther.core_presentation.ui.utils.MViewModel
import com.emmanuelguther.core_presentation.ui.utils.UiEffect
import com.emmanuelguther.core_presentation.ui.utils.UiEvent
import com.emmanuelguther.core_presentation.ui.utils.ViewModelGenericError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor() :
    MViewModel<MainViewModel.State, ViewModelGenericError, MainViewModel.Event, MainViewModel.Effect>() {

    init {
        load()
    }

    fun load() = viewModelScope.launch {
        // updateState(ViewModelState.Loaded(State()))
    }


    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnItemPressed -> {
                setEffect { Effect.NavigateToDetail }
            }
        }
    }

    data class State(val foo: String)

    sealed class Event : UiEvent {
        object OnItemPressed : Event()
    }

    sealed class Effect : UiEffect {
        object NavigateToDetail : Effect()
    }
}