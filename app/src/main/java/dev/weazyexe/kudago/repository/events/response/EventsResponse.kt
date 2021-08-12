package dev.weazyexe.kudago.repository.events.response

import com.google.gson.annotations.SerializedName
import dev.weazyexe.kudago.app.core.Transformable
import dev.weazyexe.kudago.domain.event.Event
import dev.weazyexe.kudago.domain.event.Place
import java.util.*
import kotlin.collections.ArrayList

/**
 * Ответ на запрос событий
 */
data class EventsResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("results") val events: List<EventDto>
): Transformable<List<Event>> {

    override fun transform(): List<Event> = events.map { eventDto ->
        val startDate = eventDto.dates.firstOrNull()?.startDate
        val endDate = eventDto.dates.firstOrNull()?.endDate

        Event(
            title = eventDto.title,
            shortDescription = eventDto.description,
            fullDescription = eventDto.bodyText,
            place = eventDto.place?.transform(),
            startDate = if (startDate != null) {
                Date(startDate)
            } else {
                null
            },
            endDate = if (endDate != null) {
                Date(endDate)
            } else {
                null
            },
            price = eventDto.price,
            images = eventDto.images.map { it.image }
        )
    }
}

/**
 * Dto события
 */
data class EventDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("body_text") val bodyText: String,
    @SerializedName("place") val place: PlaceDto?,
    @SerializedName("dates") val dates: List<DateDto>,
    @SerializedName("price") val price: String,
    @SerializedName("images") val images: ArrayList<Image>
)

/**
 * Dto изображения в событии
 */
data class Image(
    @SerializedName("image") val image: String
)

/**
 * Dto с данными о месте проведения события
 */
data class PlaceDto (
    @SerializedName("title") val title: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("coords") val coordinates: CoordinatesDto
): Transformable<Place> {

    override fun transform(): Place = Place(
        title = title.orEmpty(),
        address = address.orEmpty(),
        lat = coordinates.lat,
        lng = coordinates.lon
    )
}

/**
 * Dto с данными о точных координатах события
 */
data class CoordinatesDto(
    @SerializedName("lat") val lat: Double?,
    @SerializedName("lon") val lon: Double?
)

/**
 * Dto с данными о времени проведения события
 */
data class DateDto(
    @SerializedName("start_date") var startDate: Long?,
    @SerializedName("end_date") var endDate: Long?
)