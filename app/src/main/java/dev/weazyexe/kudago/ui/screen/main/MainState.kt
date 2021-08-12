package dev.weazyexe.kudago.ui.screen.main

import dev.weazyexe.kudago.app.core.LoadState
import dev.weazyexe.kudago.domain.city.City
import dev.weazyexe.kudago.domain.event.Event

data class MainState(
    val eventsLoadState: LoadState<List<Event>> = LoadState(),
    val cityLoadState: LoadState<City> = LoadState()
)