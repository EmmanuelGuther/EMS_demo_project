package com.emmanuelguther.presentation.features.main

import androidx.lifecycle.viewModelScope
import com.emmanuelguther.commons.ResultData
import com.emmanuelguther.core_presentation.ui.utils.*
import com.emmanuelguther.domain.usecase.GetHistoricUseCase
import com.emmanuelguther.presentation.mapper.toDaysEnergyHistoric
import com.emmanuelguther.presentation.model.DaysEnergyHistoric
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(private val getHistoricUseCase: GetHistoricUseCase) :
    MViewModel<MainViewModel.State, ViewModelGenericError, MainViewModel.Event, MainViewModel.Effect>() {

    init {
        loadHistoric()
    }

    private fun loadHistoric() = viewModelScope.launch {
        getHistoricUseCase.invoke().collect {
            when (it) {
                is ResultData.Failure -> updateState(ViewModelState.Error(ViewModelGenericError.Default(it.errorMessage.toString()))) //here we should parse the types of errors to act in the view accordingly
                is ResultData.Loading -> updateState(ViewModelState.Loading())
                is ResultData.Success -> updateState(ViewModelState.Loaded(State(it.data.toDaysEnergyHistoric())))
            }
        }
    }

    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnItemPressed -> {
                setEffect { Effect.NavigateToDetail }
            }
            Event.OnErrorRetry -> loadHistoric()
        }
    }

    data class State(val daysEnergyHistoric: DaysEnergyHistoric)

    sealed class Event : UiEvent {
        object OnItemPressed : Event()
        object OnErrorRetry : Event()
    }

    sealed class Effect : UiEffect {
        object NavigateToDetail : Effect()
    }
}