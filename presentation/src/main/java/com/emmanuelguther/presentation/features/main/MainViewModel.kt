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
        loadHistoric()
    }

    private fun loadHistoric() = viewModelScope.launch {
        getHistoricUseCase.invoke().collect {
            when (it) {
                is ResultData.Failure -> updateState(ViewModelState.Error(ViewModelGenericError.Default(it.errorMessage.toString()))) //here we should parse the types of errors to act in the view accordingly
                is ResultData.Loading -> updateState(ViewModelState.Loading())
                is ResultData.Success -> getLiveUseCase.invoke().collect {itLive->
                    when (itLive) {
                        is ResultData.Failure -> {}
                        is ResultData.Loading -> {}
                        is ResultData.Success -> updateState(
                            ViewModelState.Loaded(State(it.data.toDaysEnergyHistoric(),itLive.data.domainToPresentation())))
                    }
                }

            }
        }
    }

    private fun loadLiveData() = viewModelScope.launch {

    }


    override fun handleEvent(event: Event) {
        when (event) {
           is Event.OnItemPressed -> {
                setEffect { Effect.NavigateToDetail(event.data) }
            }
            Event.OnErrorRetry -> loadHistoric()
        }
    }

        data class State(val data: DaysEnergyHistoric, val liveEnergy: LiveModel)

    sealed class Event : UiEvent {
        data class OnItemPressed(val data: HourEnergyHistoric) : Event()
        object OnErrorRetry : Event()
    }

    sealed class Effect : UiEffect {
        data class NavigateToDetail(val data: HourEnergyHistoric) : Effect()
    }
}