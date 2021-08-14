package dev.weazyexe.kudago.ui.screen.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.weazyexe.core.ui.CoreViewModel
import dev.weazyexe.core.ui.LoadState
import dev.weazyexe.kudago.repository.cities.CitiesRepository
import dev.weazyexe.kudago.repository.events.EventsRepository
import dev.weazyexe.kudago.utils.extensions.handleErrors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Вью модель экрана [MainActivity]
 */
class MainViewModel(private val saved: SavedStateHandle) : CoreViewModel<MainState>() {

    @Inject
    lateinit var eventsRepository: EventsRepository

    @Inject
    lateinit var citiesRepository: CitiesRepository

    override val initialState = MainState(
        eventsLoadState = saved[EVENTS_KEY] ?: LoadState(),
        cityLoadState = saved[CITY_KEY] ?: LoadState()
    )

    override suspend fun saveState(state: MainState) {
        saved[EVENTS_KEY] = state.eventsLoadState
        saved[CITY_KEY] = state.eventsLoadState
    }

    fun onCreate() {
        if (saved.keys().isEmpty()) {
            loadEvents()
        }
    }

    fun loadEvents(isSwipeRefresh: Boolean = false) = viewModelScope.launch {
        state.copy(
            eventsLoadState = state
                .eventsLoadState
                .loading(isSwipeRefresh),
            cityLoadState =state
                .cityLoadState
                .loading()
        ).emit()

        citiesRepository.getCurrentCity()
            .flatMapConcat { slug ->
                citiesRepository.getCityBySlug(slug)
            }
            .flatMapConcat { city ->
                state.copy(
                    cityLoadState = state
                        .cityLoadState
                        .data(city)
                ).emit()

                eventsRepository.getEvents(city.slug)
            }
            .handleErrors {
                state.copy(
                    eventsLoadState = state
                        .eventsLoadState
                        .error(it)
                ).emit()
            }
            .collect { events ->
                state.copy(
                    eventsLoadState = state
                        .eventsLoadState
                        .data(events)
                ).emit()
            }
    }

    private companion object {

        const val EVENTS_KEY = "EVENTS_KEY"
        const val CITY_KEY = "CITY_KEY"
    }
}