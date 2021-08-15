package dev.weazyexe.kudago.repository.events

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.weazyexe.core.di.PerApplication
import dev.weazyexe.kudago.domain.event.Event
import kotlinx.coroutines.flow.Flow
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
    fun getEvents(city: String): Flow<PagingData<Event>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { EventsNetworkSource(eventsApi, city) }
        ).flow

    companion object {

        const val DEFAULT_PAGE = 1
        const val NETWORK_PAGE_SIZE = 20
    }
}