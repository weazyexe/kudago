package dev.weazyexe.kudago.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.kudago.repository.events.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * Вью модель экрана [MainActivity]
 */
class MainViewModel : ViewModel() {

    @Inject
    lateinit var eventsRepository: EventsRepository

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState>
        get() = _state.asStateFlow()

    fun onCreate() {
        loadEvents()
    }

    fun loadEvents(isSwipeRefresh: Boolean = false) = viewModelScope.launch {
        try {
            updateState(
                getState().copy(
                    isLoading = true,
                    isSwipeRefresh = isSwipeRefresh,
                    error = null
                )
            )
            val events = eventsRepository.getEvents(DEFAULT_CITY)
            updateState(
                getState().copy(
                    events = events,
                    isLoading = false,
                    isSwipeRefresh = false
                )
            )
        } catch (e: Exception) {
            updateState(
                getState().copy(
                    events = emptyList(),
                    isLoading = false,
                    isSwipeRefresh = false,
                    error = e
                )
            )
        }
    }

    private fun getState(): MainState = state.value

    private suspend fun updateState(state: MainState) {
        _state.emit(state)
    }

    private companion object {

        const val DEFAULT_CITY = "msk"
    }
}