package dev.weazyexe.kudago.ui.screen.main

import dev.weazyexe.kudago.domain.event.Event
import java.lang.Exception

data class MainState(
    val events: List<Event> = emptyList(),
    val error: Exception? = null,
    val isLoading: Boolean = false,
    val isSwipeRefresh: Boolean = false
)