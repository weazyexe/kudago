package dev.weazyexe.kudago.ui.screen.cities

import dev.weazyexe.core.ui.Effect
import dev.weazyexe.core.ui.LoadState
import dev.weazyexe.core.ui.State
import dev.weazyexe.kudago.domain.city.City
import dev.weazyexe.kudago.ui.screen.cities.adapter.CityUiModel

/**
 * Базовое состояние экрана [CitiesActivity]
 */
data class CitiesState(
    val cities: LoadState<List<CityUiModel>> = LoadState()
) : State


/**
 * Сайд-эффекты экрана [CitiesActivity]
 */
sealed class CitiesEffect : Effect {

    data class ChooseCity(val city: City) : CitiesEffect()
}