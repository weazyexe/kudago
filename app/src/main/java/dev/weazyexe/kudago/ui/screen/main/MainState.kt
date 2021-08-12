package dev.weazyexe.kudago.ui.screen.main

import dev.weazyexe.kudago.domain.event.Event

data class MainState(
    val events: List<Event> = emptyList(),
    val hasError: Boolean = false,
    val isLoading: Boolean = false
)