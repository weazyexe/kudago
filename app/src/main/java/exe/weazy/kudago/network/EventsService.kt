package exe.weazy.kudago.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://kudago.com/public-api/v1.4/"

interface EventsService {
    @GET(
        "events/?fields=id,dates,title,place,price,description,images,body_text&expand=place,dates"
                + "&order_by=-publication_date&text_format=text"
    )
    fun getEvents(@Query("location") city : String): Call<EventsResponse>
}