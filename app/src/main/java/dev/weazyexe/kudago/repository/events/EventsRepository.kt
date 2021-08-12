package dev.weazyexe.kudago.repository.events

import dev.weazyexe.kudago.domain.event.Event
import javax.inject.Inject

/**
 * Репозиторий с данными о событиях
 */
class EventsRepository @Inject constructor(
    private val eventsApi: EventsApi
) {

    /**
     * Получить список событий
     */
    suspend fun getEvents(city: String): List<Event> =
        eventsApi.getEvents(city).transform()
}