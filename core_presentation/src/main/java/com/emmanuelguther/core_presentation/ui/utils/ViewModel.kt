package com.emmanuelguther.core_presentation.ui.utils

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MViewModel<C, E, Event : UiEvent, Effect : UiEffect> :
    StatelessViewModel<Event, Effect>(), LifecycleObserver {
    companion object {
        var dispatcher: CoroutineDispatcher = Dispatchers.Default
    }

    val state: Flow<ViewModelState<C, E>> get() = _state

    private var _state: MutableStateFlow<ViewModelState<C, E>> = MutableStateFlow(
        ViewModelState.Loading()
    )

    fun currentState(): ViewModelState<C, E> = _state.value

    fun updateState(newState: ViewModelState<C, E>) {
        _state.value = newState
    }

    protected inline fun async(crossinline block: suspend () -> Unit) =
        viewModelScope.launch {
            block()
        }

    protected suspend inline fun <T> await(crossinline block: suspend () -> T): T =
        withContext(dispatcher) { block() }

    protected inline fun asyncFlow(crossinline block: suspend () -> Unit) = async {
        withContext(dispatcher) {
            block()
        }
    }

}

sealed class ViewModelState<C, out E> {
    companion object {
        val initialState: ViewModelState<Nothing, Nothing> = Loading()
    }

    data class Loading<C, E>(val refreshing: Boolean = false) : ViewModelState<C, E>()
    data class Error<C, E>(val error: E) : ViewModelState<C, E>()
    data class Loaded<C, E>(val content: C, val error: E? = null, val refreshing: Boolean = false) :
        ViewModelState<C, E>()

    fun loading(): Boolean = this is Loading

    fun refreshingContent(): Boolean = when (this) {
        is Loading -> this.refreshing
        is Loaded<*, *> -> this.refreshing
        else -> false
    }

    fun containsAnyError(): Boolean = error() != null

    fun content(): C? = when (this) {
        is Loaded -> this.content
        else -> null
    }

    fun withContentIfLoaded(newContent: (C) -> C): ViewModelState<C, E> = when (this) {
        is Loaded -> Loaded(
            content = newContent(this.content),
            error = this.error,
            refreshing = this.refreshing
        )
        is Loading -> this
        is Error -> this
    }

    fun error(): E? = when (this) {
        is Error -> error
        is Loaded -> error
        is Loading -> null
    }

    fun hasContent(): Boolean = content() != null

    fun markAsRefreshing(refreshing: Boolean = true): ViewModelState<C, E> = when (this) {
        is Loading -> Loading(refreshing)
        is Error -> if (refreshing) Loading(refreshing) else this
        is Loaded -> copy(refreshing = refreshing)
    }

    fun <E> withError(error: E): ViewModelState<C, E> = when (this) {
        is Loading -> Error(error)
        is Error -> Error(error)
        is Loaded -> Loaded(error = error, content = this.content, refreshing = this.refreshing)
    }

    fun removeError(): ViewModelState<C, E> = when (this) {
        is Loading -> this
        is Error -> Loading()
        is Loaded -> Loaded(content = this.content, refreshing = this.refreshing, error = null)
    }
}

sealed class ViewModelGenericError {
    object Connection : ViewModelGenericError()
    data class Default(val message: String) : ViewModelGenericError()
}

abstract class StatelessViewModel<Event : UiEvent, Effect : UiEffect> : ViewModel() {

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    open fun init() {
        viewModelScope.launch {
            extraInitializationSteps()
        }
    }

    /**
     * Reserved function for extra steps needed on View Model initialization.
     * Trackings, navigation, sending view events, etc.
     */
    protected open suspend fun extraInitializationSteps() {}

    /**
     * Start listening to Event
     */
    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * Handle each event
     */
    abstract fun handleEvent(event: Event)

    /**
     * Set new Event
     */
    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    /**
     * Set new Effect
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}

interface UiEvent
interface UiEffect
