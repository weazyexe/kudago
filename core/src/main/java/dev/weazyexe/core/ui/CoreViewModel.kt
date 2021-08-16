package dev.weazyexe.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Базовая [ViewModel] для всех экранов приложения
 */
abstract class CoreViewModel<S : State, E : Effect> : ViewModel() {

    private val _uiState by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()
    protected val state: S
        get() = uiState.value

    private val _effects = Channel<E>(Channel.BUFFERED)
    val effects: Flow<E>
        get() = _effects.receiveAsFlow()

    /**
     * Первоначальное состояние экрана
     */
    protected abstract val initialState: S

    /**
     * Сохранение состояния, выполняется на IO потоке
     */
    abstract suspend fun saveState(state: S)

    /**
     * Триггер сайд-эффекта
     */
    protected fun E.emit() = viewModelScope.launch {
        _effects.send(this@emit)
    }

    /**
     * Вызов обновления состояния экрана
     * и сохранение состояния
     */
    protected suspend fun S.emit() {
        _uiState.emit(this)

        viewModelScope.launch(Dispatchers.IO) {
            saveState(this@emit)
        }
    }
}