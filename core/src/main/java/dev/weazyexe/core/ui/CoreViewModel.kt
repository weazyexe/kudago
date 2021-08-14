package dev.weazyexe.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Базовая [ViewModel] для всех экранов приложения
 */
abstract class CoreViewModel<S : State> : ViewModel() {

    private val _uiState by lazy { MutableStateFlow(initialState) }
    val uiState: StateFlow<S>
        get() = _uiState.asStateFlow()
    protected val state: S
        get() = uiState.value

    /**
     * Первоначальное состояние экрана
     */
    protected abstract val initialState: S

    /**
     * Сохранение состояния, выполняется на IO потоке
     */
    abstract suspend fun saveState(state: S)

    /**
     * Вызов обновления состояния экрана и
     */
    protected suspend fun S.emit() {
        _uiState.emit(this)

        viewModelScope.launch(Dispatchers.IO) {
            saveState(this@emit)
        }
    }
}