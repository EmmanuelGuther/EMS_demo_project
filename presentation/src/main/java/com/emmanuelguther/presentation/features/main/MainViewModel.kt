package com.emmanuelguther.presentation.features.main

import androidx.lifecycle.viewModelScope
import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.core_presentation.ui.utils.*
import com.emmanuelguther.domain.usecase.GetHistoricUseCase
import com.emmanuelguther.domain.usecase.GetLiveUseCase
import com.emmanuelguther.presentation.mapper.domainToPresentation
import com.emmanuelguther.presentation.mapper.toDaysEnergyHistoric
import com.emmanuelguther.presentation.model.DaysEnergyHistoric
import com.emmanuelguther.presentation.model.HourEnergyHistoric
import com.emmanuelguther.presentation.model.LiveModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(private val getHistoricUseCase: GetHistoricUseCase, private val getLiveUseCase: GetLiveUseCase) :
    MViewModel<MainViewModel.State, ViewModelGenericError, MainViewModel.Event, MainViewModel.Effect>() {

    init {
        //updateState(State(emptyMap<>()))
        loadHistoric()
        loadLiveData()
    }

    private fun loadHistoric() = viewModelScope.launch {
        getHistoricUseCase.invoke().collect {
            when (it) {
                is ResultData.Failure -> updateState(ViewModelState.Error(ViewModelGenericError.Default(it.errorMessage.toString()))) //here we should parse the types of errors to act in the view accordingly
                is ResultData.Loading -> updateState(ViewModelState.Loading())
                is ResultData.Success -> {
                    val currentState = getCurrentState()
                    currentState?.add(State.DaysEHistoric(it.data.toDaysEnergyHistoric()))
                    currentState?.toSet()?.let { itCurrentState ->
                        updateState(ViewModelState.Loaded(State(itCurrentState)))
                    }
                }
            }
        }
    }

    private fun loadLiveData() = viewModelScope.launch {
        getLiveUseCase.invoke().collect {
            when (it) {
                is ResultData.Failure -> {}
                is ResultData.Loading -> {}
                is ResultData.Success -> {
                    val currentState = getCurrentState()
                    currentState?.add(State.LiveE(it.data.domainToPresentation()))
                    currentState?.toSet()?.let { itCurrentState ->
                        updateState(ViewModelState.Loaded(State(itCurrentState)))
                    }
                }
            }
        }
    }

    private fun getCurrentState() = when (currentState().content()?.data) {
        null -> emptySet<Any>().toMutableSet()
        else -> currentState().content()?.data?.toMutableSet()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnItemPressed -> {
                setEffect { Effect.NavigateToDetail(event.data) }
            }
            Event.OnErrorRetry -> {
                loadHistoric()
                loadLiveData()
            }
        }
    }

    data class State(val data: Set<Any>) {
        data class DaysEHistoric(val data: DaysEnergyHistoric)
        data class LiveE(val data: LiveModel)
    }


    sealed class Event : UiEvent {
        data class OnItemPressed(val data: HourEnergyHistoric) : Event()
        object OnErrorRetry : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateToDetail(val data: HourEnergyHistoric) : Effect()
    }
}