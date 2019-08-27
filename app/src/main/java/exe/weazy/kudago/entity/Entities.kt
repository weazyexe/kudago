package exe.weazy.kudago.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventsResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val events: List<Event>
)

data class CityResponse(
    @SerializedName("slug")
    val slug : String,
    @SerializedName("name")
    val name: String
)

data class Event(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val shortDescription: String,
    @SerializedName("body_text")
    val fullDescription: String,
    @SerializedName("place")
    var place: Place?,
    @SerializedName("dates")
    var dates: List<Date>,
    @SerializedName("price")
    var price: String,
    @SerializedName("images")
    val images: ArrayList<Image>
) : Serializable

data class City(
    @SerializedName("slug")
    var slug : String,
    @SerializedName("name")
    var title : String,
    var checked : Boolean = false
) : Serializable

data class Place (
    @SerializedName("title")
    val title: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("coords")
    val coordinates: Coordinates
) : Serializable

data class Image(
    @SerializedName("image")
    val image: String
) : Serializable

data class Date(
    @SerializedName("start_date")
    var startDate: String?,
    @SerializedName("end_date")
    var endDate: String?
) : Serializable

data class Coordinates(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
) : Serializable