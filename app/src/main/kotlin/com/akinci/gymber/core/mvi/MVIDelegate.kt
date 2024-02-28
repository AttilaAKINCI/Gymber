package com.akinci.gymber.core.mvi


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MVIDelegate<State, Action, Effect> internal constructor(
    initialUiState: State,
) : MVI<State, Action, Effect> {

    private val _state = MutableStateFlow(initialUiState)
    override val state: StateFlow<State> = _state.asStateFlow()

    private val _effect by lazy { Channel<Effect>() }
    override val effect: Flow<Effect> by lazy { _effect.receiveAsFlow() }

    override fun onAction(action: Action) {}

    override fun updateState(block: State.() -> State) {
        _state.update { block(it) }
    }

    override fun CoroutineScope.sendEffect(effect: Effect) {
        launch { _effect.send(effect) }
    }
}

fun <State, Action, Effect> mvi(
    initialState: State
): MVI<State, Action, Effect> = MVIDelegate(initialState)