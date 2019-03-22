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

    /*fun getCities(cityResponseCallback: CityResponseCallback<String>) {
        NetworkService.instance.service.getCities()
            .enqueue(object : Callback<String> {

                override fun onFailure(call: Call<String>, t: Throwable) {
                    cityResponseCallback.onFailure("Getting cities error")
                }

                override fun onResponse(call: Call<List<City>>, response: Response<String>) {
                    val citiesResponse = response.body()

                    if (citiesResponse != null) {
                        cityResponseCallback.onSuccess(citiesResponse)
                    } else {
                        cityResponseCallback.onFailure("Getting cities error")
                    }
                }
            })
    }*/
}