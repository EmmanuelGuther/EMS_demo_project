package com.emmanuelguther.presentation

import com.emmanuelguther.core_presentation.ui.utils.ViewModelGenericError
import com.emmanuelguther.core_presentation.ui.utils.ViewModelState
import com.emmanuelguther.domain.usecase.GetHistoricUseCase
import com.emmanuelguther.domain.usecase.GetLiveUseCase
import com.emmanuelguther.presentation.features.main.MainViewModel
import com.emmanuelguther.presentation.mapper.domainToPresentation
import com.emmanuelguther.presentation.mapper.toDaysEnergyHistoric
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelUnitTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Success)
    private val getHistoricUseCase = GetHistoricUseCase(historicRepository)
    private val getLiveUseCase = GetLiveUseCase(historicRepository)

    @Before
    fun setUp() {}


    @Test
    fun `when app searches for historic data and result is failure`() = runBlocking {
        val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Failure)
        val getHistoricUseCase = GetHistoricUseCase(historicRepository)
        val vm = MainViewModel(getHistoricUseCase, getLiveUseCase)
        vm.init()
        val job = this.launch {
            vm.state.collect {
                assertEquals(ViewModelState.Error<String, ViewModelGenericError>(error = ViewModelGenericError.Connection), it)
            }
        }
        job.cancel()
    }

    @Test
    fun `when app searches for live energy data and result is failure`() = runBlocking {
        val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Failure)
        val getLiveUseCase = GetLiveUseCase(historicRepository)
        val vm = MainViewModel(getHistoricUseCase, getLiveUseCase)
        vm.init()
        val job = this.launch {
            vm.state.collect {
                assertEquals(ViewModelState.Error<String, ViewModelGenericError>(error = ViewModelGenericError.Connection), it)
            }
        }
        job.cancel()
    }

    @Test
    fun `when app searches for historic data and result is success`() = runBlocking {
        val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Success)
        val getHistoricUseCase = GetHistoricUseCase(historicRepository)
        val vm = MainViewModel(getHistoricUseCase, getLiveUseCase)
        vm.init()
        val job = this.launch {
            vm.state.collect {
                assertEquals(
                    ViewModelState.Loaded(
                        content = MainViewModel.State.DaysEHistoric(listOf(Fakes.historicData).toDaysEnergyHistoric()),
                        error = null,
                        refreshing = false
                    ), it
                )
            }
        }
        job.cancel()
    }
    @Test
    fun `when app searches for live energy data and result is success`() = runBlocking {
        val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Success)
        val getLiveUseCase = GetLiveUseCase(historicRepository)
        val vm = MainViewModel(getHistoricUseCase, getLiveUseCase)
        vm.init()
        val job = this.launch {
            vm.state.collect {
                assertEquals(
                    ViewModelState.Loaded(
                        content = MainViewModel.State.LiveE(Fakes.liveData.domainToPresentation()),
                        error = null,
                        refreshing = false
                    ), it
                )
            }
        }
        job.cancel()
    }

    @Test
    fun `when user click onRetry then return success`() = runBlocking {
        val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Success)
        val getHistoricUseCase = GetHistoricUseCase(historicRepository)
        val vm = MainViewModel(getHistoricUseCase, getLiveUseCase)
        vm.handleEvent(MainViewModel.Event.OnErrorRetry)
        val job = this.launch {
            vm.state.collect {
                assertEquals(
                    ViewModelState.Loaded(
                        content = MainViewModel.State.DaysEHistoric(listOf(Fakes.historicData).toDaysEnergyHistoric()),
                        error = null,
                        refreshing = false
                    ), it
                )
            }
        }
        job.cancel()
    }
    
    @Test
    fun `when user click onItem then launch effect`() = runBlocking {
        val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Success)
        val getHistoricUseCase = GetHistoricUseCase(historicRepository)
        val vm = MainViewModel(getHistoricUseCase, getLiveUseCase)
        val hourEnergyHistoric=   listOf(Fakes.historicData).toDaysEnergyHistoric().first().hourEnergyHistoric.first()
        vm.handleEvent(MainViewModel.Event.OnItemPressed(hourEnergyHistoric))
        val job = this.launch {
            vm.effect.collectLatest {
                assertEquals(hourEnergyHistoric, it)
            }
        }
        job.cancel()
    }

    @After
    fun clean() {
        Dispatchers.resetMain()
    }

}