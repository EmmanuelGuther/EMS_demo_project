package com.emmanuelguther.presentation.features.detail

import androidx.lifecycle.*
import com.emmanuelguther.core_presentation.ui.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor() :
    MViewModel<DetailViewModel.State, ViewModelGenericError, DetailViewModel.Event, DetailViewModel.Effect>(),DefaultLifecycleObserver{
    lateinit var solar: String
    lateinit var grid: String
    lateinit var quasar: String

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        load()
    }

    private fun load() = viewModelScope.launch {
        updateState(ViewModelState.Loaded(State(solar.toDouble().toFloat(), grid.toDouble().toFloat(), quasar.toDouble().toFloat())))
    }

    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnButtonPressed -> {
                setEffect { Effect.NavigateToMain }
            }
        }
    }

    data class State(val solar: Float, val grid: Float, val quasar: Float)

    sealed class Event : UiEvent {
        object OnButtonPressed : Event()
    }

    sealed class Effect : UiEffect {
        object NavigateToMain : Effect()
    }
}