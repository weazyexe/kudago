package exe.weazy.kudago.network

import exe.weazy.kudago.entity.City
import exe.weazy.kudago.entity.EventsResponse
import retrofit2.Callback

interface Repository {
    fun loadEvents(slug: String, callback: Callback<EventsResponse>)
    fun loadCities(callback: Callback<List<City>>)
}