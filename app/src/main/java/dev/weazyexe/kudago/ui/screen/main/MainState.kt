package dev.weazyexe.kudago.ui.screen.main

import androidx.paging.PagingData
import dev.weazyexe.core.ui.Effect
import dev.weazyexe.core.ui.LoadState
import dev.weazyexe.core.ui.State
import dev.weazyexe.kudago.domain.city.City
import dev.weazyexe.kudago.domain.event.Event

/**
 * Состояние экрана [MainActivity]
 */
data class MainState(
    val eventsLoadState: LoadState<PagingData<Event>> = LoadState(),
    val cityLoadState: LoadState<City> = LoadState()
) : State


/**
 * Сайд-эффекты экрана [MainActivity]
 */
sealed class MainEffect : Effect {

    object OpenCitiesScreen : MainEffect()
}