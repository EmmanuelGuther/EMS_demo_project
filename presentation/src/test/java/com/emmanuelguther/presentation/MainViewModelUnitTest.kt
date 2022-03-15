package com.emmanuelguther.presentation

import com.emmanuelguther.core_presentation.ui.utils.ViewModelGenericError
import com.emmanuelguther.core_presentation.ui.utils.ViewModelState
import com.emmanuelguther.domain.usecase.GetHistoricUseCase
import com.emmanuelguther.domain.usecase.GetLiveUseCase
import com.emmanuelguther.presentation.features.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//@ExperimentalCoroutinesApi
//class MainViewModelUnitTest {
//    @get:Rule
//    val coroutinesTestRule = CoroutinesTestRule()
//
//    private val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Success)
//    private val getHistoricUseCase = GetHistoricUseCase(historicRepository)
//    private val getLiveUseCase = GetLiveUseCase(historicRepository)
//
//
//    private fun vm() = MainViewModel(getHistoricUseCase, getLiveUseCase)
//
//    @Before
//    fun setUp() {
//    }
//
//
//    @Test
//    fun `when app searches for historic data and result is failure`() = runBlocking {
//        val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Failure)
//        val getHistoricUseCase = GetHistoricUseCase(historicRepository)
//        val vm = MainViewModel(getHistoricUseCase, getLiveUseCase)
//        vm.init()
//        val job = this.launch {
//            vm.state.collect {
//                assertEquals(ViewModelState.Error<String, ViewModelGenericError>(error = ViewModelGenericError.Connection), it)
//            }
//        }
//        job.cancel()
//    }
//
//    @Test
//    fun `when app searches for historic data and result is success`() = runBlocking {
//        val historicRepository = Fakes.FakeEnergyRepository(Fakes.Companion.ResultType.Failure)
//        val getHistoricUseCase = GetHistoricUseCase(historicRepository)
//        val vm = MainViewModel(getHistoricUseCase, getLiveUseCase)
//        vm.init()
//        val job = this.launch {
//            vm.state.collect {
//                assertEquals(
//                    ViewModelState.Loaded(
//                        content = MainViewModel.State.Started(gameModel, username),
//                        error = null,
//                        refreshing = false
//                    ), it
//                )
//            }
//        }
//        job.cancel()
//    }
//
//    @Test
//    fun `when app is ready to start the game and the result is failure`() = runBlocking {
//        val gameRepository = Fakes.FakeGameRepository(Fakes.Companion.ResultType.Failure)
//        val startGameUseCase = StartGameUseCase(gameRepository)
//        val vm = MainViewModel(getUserUseCase, getRivalUseCase, startGameUseCase, nextRoundGameUseCase)
//        vm.handleEvent(MainViewModel.Event.OnStartButtonPressed)
//        val job = this.launch {
//            vm.state.collect {
//                assertEquals(ViewModelState.Error<String, ViewModelGenericError>(error = ViewModelGenericError.Connection), it)
//            }
//        }
//        job.cancel()
//    }
//
//
//    @Test
//    fun `if user clicks on the card, then the next round return failure`() = runBlocking {
//        val gameRepositoryforStart = Fakes.FakeGameRepository(Fakes.Companion.ResultType.Success)
//        val startGameUseCase = StartGameUseCase(gameRepositoryforStart)
//        val gameRepository = Fakes.FakeGameRepository(Fakes.Companion.ResultType.Failure)
//        val nextRoundGameUseCase = NextRoundGameUseCase(gameRepository)
//        val vm = MainViewModel(getUserUseCase, getRivalUseCase, startGameUseCase, nextRoundGameUseCase)
//        vm.handleEvent(MainViewModel.Event.OnStartButtonPressed)
//        vm.handleEvent(MainViewModel.Event.OnClickToRevealCards)
//        val job = this.launch {
//            vm.state.collect {
//                assertEquals(ViewModelState.Error<String, ViewModelGenericError>(error = ViewModelGenericError.RoundError), it)
//            }
//        }
//        job.cancel()
//    }
//
//    @Test
//    fun `if user clicks on the card, then the next round return success`() = runBlocking {
//        val gameRepository = Fakes.FakeGameRepository(Fakes.Companion.ResultType.Success)
//        val startGameUseCase = StartGameUseCase(gameRepository)
//        val nextRoundGameUseCase = NextRoundGameUseCase(gameRepository)
//        val vm = MainViewModel(getUserUseCase, getRivalUseCase, startGameUseCase, nextRoundGameUseCase)
//        vm.handleEvent(MainViewModel.Event.OnStartButtonPressed)
//        vm.handleEvent(MainViewModel.Event.OnClickToRevealCards)
//        vm.handleEvent(MainViewModel.Event.OnClickToRevealCards)
//        val job = this.launch {
//            vm.state.collect {
//                assertEquals(
//                    ViewModelState.Loaded(
//                        content = MainViewModel.State.Started(gameModel, username),
//                        error = null,
//                        refreshing = false
//                    ), it
//                )
//            }
//        }
//        job.cancel()
//    }
//
//
//    @After
//    fun clean() {
//        Dispatchers.resetMain()
//    }
//
//}