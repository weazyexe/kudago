package exe.weazy.kudago.network

import exe.weazy.kudago.entity.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestsRepository {

    private object Holder {
        val INSTANCE = RequestsRepository()
    }

    companion object {
        val instance: RequestsRepository by lazy { Holder.INSTANCE }
    }

    fun getEvents(citySlug : String, eventsResponseCallback: EventsResponseCallback<EventsResponse>) {
        NetworkService.instance.service.getEvents(citySlug)
            .enqueue(object : Callback<EventsResponse> {

                override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                    eventsResponseCallback.onFailure("Getting events error")
                }

                override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                    val eventsResponse = response.body()

                    if (eventsResponse != null) {
                        eventsResponseCallback.onSuccess(eventsResponse)
                    } else {
                        eventsResponseCallback.onFailure("Getting events error")
                    }
                }
            })
    }

    fun getCities(citiesResponseCallback: CitiesResponseCallback<List<City>>) {
        NetworkService.instance.service.getCities()
            .enqueue(object : Callback<List<City>> {

                override fun onFailure(call: Call<List<City>>, t: Throwable) {
                    citiesResponseCallback.onFailure("Getting cities error")
                }

                override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                    val citiesResponse = response.body()

                    if (citiesResponse != null) {
                        citiesResponseCallback.onSuccess(citiesResponse)
                    } else {
                        citiesResponseCallback.onFailure("Getting cities error")
                    }
                }
            })
    }

    fun getCityBySlug(slug: String, cityResponseCallback: CityResponseCallback<CityResponse>) {
        NetworkService.instance.service.getCityBySlug(slug)
            .enqueue(object : Callback<CityResponse> {

                override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                    cityResponseCallback.onFailure("Getting city error")
                }

                override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
                    val cityResponse = response.body()

                    if (cityResponse != null) {
                        cityResponseCallback.onSuccess(cityResponse)
                    } else {
                        cityResponseCallback.onFailure("Getting city error")
                    }
                }
            })
    }
}