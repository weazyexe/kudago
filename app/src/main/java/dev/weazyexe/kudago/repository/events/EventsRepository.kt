package dev.weazyexe.kudago.repository.events

import dev.weazyexe.kudago.app.di.PerApplication
import dev.weazyexe.kudago.domain.event.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Репозиторий с данными о событиях
 */
@PerApplication
class EventsRepository @Inject constructor(
    private val eventsApi: EventsApi
) {

    /**
     * Получить список событий
     */
    fun getEvents(city: String): Flow<List<Event>> = flow {
        emit(eventsApi.getEvents(city).transform())
    }
}