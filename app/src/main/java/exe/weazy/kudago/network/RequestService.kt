package exe.weazy.kudago.network

import exe.weazy.kudago.entity.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://kudago.com/public-api/v1.4/"

interface RequestService {
    @GET(
        "events/?fields=id,dates,title,place,price,description,images,body_text&expand=place,dates"
                + "&order_by=-publication_date&text_format=text"
    )
    fun getEvents(@Query("location") city : String): Call<EventsResponse>

    @GET("locations/?lang=ru")
    fun getCities() : Call<List<City>>

    @GET("/locations/{location}")
    fun getCityBySlug(@Path("location") slug: String) : Call<String>
}