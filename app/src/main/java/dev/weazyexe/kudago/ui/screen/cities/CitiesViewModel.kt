package dev.weazyexe.kudago.ui.screen.cities

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.weazyexe.core.ui.CoreViewModel
import dev.weazyexe.core.ui.LoadState
import dev.weazyexe.kudago.domain.city.City
import dev.weazyexe.kudago.repository.cities.CitiesRepository
import dev.weazyexe.kudago.ui.screen.cities.CitiesEffect.ChooseCity
import dev.weazyexe.kudago.ui.screen.cities.adapter.CityUiModel
import dev.weazyexe.kudago.utils.extensions.handleErrors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Вью модель экрана [CitiesActivity]
 */
class CitiesViewModel(
    private val saved: SavedStateHandle
) : CoreViewModel<CitiesState, CitiesEffect>() {

    @Inject
    lateinit var citiesRepository: CitiesRepository

    override val initialState: CitiesState = CitiesState(
        cities = saved[CITIES_KEY] ?: LoadState()
    )

    override suspend fun saveState(state: CitiesState) {
        saved[CITIES_KEY] = state.cities
    }

    fun onCreate() {
        if (saved.keys().isEmpty()) {
            loadCities()
        }
    }

    private fun loadCities() = viewModelScope.launch {
        state.copy(cities = LoadState.loading()).emit()

        val getCurrentCityFlow = citiesRepository.getCurrentCity()
        val getCitiesFlow = citiesRepository.getCities()

        getCurrentCityFlow.zip(getCitiesFlow) { city, cities -> city to cities }
            .handleErrors {
                // TODO
            }
            .collectLatest { (city, cities) ->
                val mappedCities = cities.map {
                    CityUiModel(
                        city = it,
                        isChecked = it.slug == city
                    )
                }

                state.copy(
                    cities = LoadState.data(mappedCities)
                ).emit()
            }
    }

    fun pickCity(city: City) {
        ChooseCity(city).emit()
    }

    private companion object {

        const val CITIES_KEY = "CITIES_KEY"
    }
}