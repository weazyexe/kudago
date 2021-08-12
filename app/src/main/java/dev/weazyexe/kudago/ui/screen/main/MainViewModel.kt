package dev.weazyexe.kudago.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.kudago.repository.cities.CitiesRepository
import dev.weazyexe.kudago.repository.events.EventsRepository
import dev.weazyexe.kudago.utils.extensions.handleErrors
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Вью модель экрана [MainActivity]
 */
class MainViewModel : ViewModel() {

    @Inject
    lateinit var eventsRepository: EventsRepository

    @Inject
    lateinit var citiesRepository: CitiesRepository

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState>
        get() = _state.asStateFlow()

    fun onCreate() {
        loadEvents()
    }

    fun loadEvents(isSwipeRefresh: Boolean = false) = viewModelScope.launch {
        updateState(
            getState().copy(
                eventsLoadState = getState()
                    .eventsLoadState
                    .loading(isSwipeRefresh),
                cityLoadState = getState()
                    .cityLoadState
                    .loading()
            )
        )

        citiesRepository.getCurrentCity()
            .flatMapConcat { slug ->
                citiesRepository.getCityBySlug(slug)
            }
            .flatMapConcat { city ->
                updateState(
                    getState().copy(
                        cityLoadState = getState()
                            .cityLoadState
                            .data(city)
                    )
                )

                eventsRepository.getEvents(city.slug)
            }
            .handleErrors {
                updateState(
                    getState().copy(
                        eventsLoadState = getState()
                            .eventsLoadState
                            .error(it)
                    )
                )
            }
            .collect { events ->
                updateState(
                    getState().copy(
                        eventsLoadState = getState()
                            .eventsLoadState
                            .data(events)
                    )
                )
            }
    }

    private fun getState(): MainState = state.value

    private suspend fun updateState(state: MainState) {
        _state.emit(state)
    }
}