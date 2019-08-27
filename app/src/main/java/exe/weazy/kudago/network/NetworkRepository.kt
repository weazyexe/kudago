package exe.weazy.kudago.network

import exe.weazy.kudago.entity.City
import exe.weazy.kudago.entity.EventsResponse
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepository : Repository {

    private var retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var service = retrofit.create(RequestService::class.java)

    override fun loadEvents(slug : String, callback: Callback<EventsResponse>) {
        service.getEvents(slug).enqueue(callback)
    }

    override fun loadCities(callback: Callback<List<City>>) {
        service.getCities().enqueue(callback)
    }
}