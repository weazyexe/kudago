package dev.weazyexe.kudago.ui.screen.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.weazyexe.core.ui.CoreViewModel
import dev.weazyexe.core.ui.LoadState
import dev.weazyexe.kudago.domain.city.City
import dev.weazyexe.kudago.repository.cities.CitiesRepository
import dev.weazyexe.kudago.repository.events.EventsRepository
import dev.weazyexe.kudago.ui.screen.main.MainEffect.*
import dev.weazyexe.kudago.utils.extensions.handleErrors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Вью модель экрана [MainActivity]
 */
class MainViewModel(
    private val saved: SavedStateHandle
) : CoreViewModel<MainState, MainEffect>() {

    @Inject
    lateinit var eventsRepository: EventsRepository

    @Inject
    lateinit var citiesRepository: CitiesRepository

    override val initialState = MainState(
        cityLoadState = saved[CITY_KEY] ?: LoadState()
    )

    override suspend fun saveState(state: MainState) {
        saved[CITY_KEY] = state.cityLoadState
    }

    fun onCreate() {
        if (saved.keys().isEmpty()) {
            loadCityAndEvents()
        }
    }

    fun onPickCityClicked() {
        OpenCitiesScreen.emit()
    }

    fun onCityPicked(city: City?) = viewModelScope.launch {
        city ?: return@launch

        state.copy(
            cityLoadState = LoadState.data(city),
            eventsLoadState = LoadState.loading()
        ).emit()

        citiesRepository.updateCurrentCity(city.slug)
            .collectLatest { success ->
                if (success) {
                    loadEvents(city.slug)
                }
            }
    }

    fun loadCityAndEvents(isSwipeRefresh: Boolean = false) = viewModelScope.launch {
        state.copy(
            eventsLoadState = LoadState.loading(
                isSwipeRefresh,
                state.eventsLoadState.data
            ),
            cityLoadState = LoadState.loading()
        ).emit()

        citiesRepository.getCurrentCity()
            .flatMapConcat { slug ->
                citiesRepository.getCityBySlug(slug)
            }
            .collectLatest { city ->
                state.copy(
                    cityLoadState = LoadState.data(city)
                ).emit()

                loadEvents(city.slug)
            }
    }

    private suspend fun loadEvents(slug: String) {
        eventsRepository.getEvents(slug)
            .handleErrors { exception ->
                state.copy(
                    eventsLoadState = LoadState.error(
                        exception,
                        state.eventsLoadState.data
                    )
                ).emit()
            }
            .cachedIn(viewModelScope)
            .collectLatest {
                state.copy(
                    eventsLoadState = LoadState.data(it)
                ).emit()
            }
    }

    private companion object {

        const val CITY_KEY = "CITY_KEY"
    }
}