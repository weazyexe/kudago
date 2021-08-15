package dev.weazyexe.kudago.repository.events

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.weazyexe.kudago.domain.event.Event
import dev.weazyexe.kudago.repository.events.EventsRepository.Companion.DEFAULT_PAGE

/**
 * Источник данных о событиях из сети с пагинацией
 */
class EventsNetworkSource(
    private val eventsApi: EventsApi,
    private val slug: String
) : PagingSource<Int, Event>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        val page = params.key ?: DEFAULT_PAGE
        val pageSize = params.loadSize

        return try {
            val events = eventsApi.getEvents(slug, page, pageSize).transform()
            val nextKey = if (events.isEmpty()) {
                null
            } else {
                page + 1
            }
            LoadResult.Page(
                data = events,
                prevKey = if (page == DEFAULT_PAGE) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        return state.anchorPosition
    }
}