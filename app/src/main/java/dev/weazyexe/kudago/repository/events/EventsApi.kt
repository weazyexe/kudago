package dev.weazyexe.kudago.repository.events

import dev.weazyexe.kudago.repository.events.response.EventsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API событий
 */
interface EventsApi {

    /**
     * Получить список событий в [city]
     */
    @GET("events/")
    suspend fun getEvents(
        @Query("location") city: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("fields") fields: String = "id,dates,title,place,price,description,images,body_text",
        @Query("expand") expand: String = "place",
        @Query("order_by") orderBy: String = "-publication_date",
        @Query("text_format") textFormat: String = "text"
    ): EventsResponse
}