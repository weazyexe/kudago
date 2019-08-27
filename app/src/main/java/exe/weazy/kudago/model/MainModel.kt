package exe.weazy.kudago.model

import exe.weazy.kudago.arch.MainContract
import exe.weazy.kudago.entity.City
import exe.weazy.kudago.entity.EventsResponse
import exe.weazy.kudago.network.BASE_URL
import exe.weazy.kudago.network.NetworkRepository
import exe.weazy.kudago.network.RequestService
import exe.weazy.kudago.presenter.MainPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainModel(private val presenter : MainPresenter) : MainContract.Model {

    private val repository = NetworkRepository()

    override fun fetchEvents(slug : String) {
        repository.loadEvents(slug, object : Callback<EventsResponse> {

            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                presenter.onEventsLoadFailure(t)
            }

            override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                val eventsResponse = response.body()

                if (eventsResponse != null) {
                    presenter.onEventsLoadSuccess(eventsResponse.events)
                } else {
                    presenter.onEventsLoadFailure(Throwable("Events response is null"))
                }
            }
        })
    }

    override fun fetchCities() {
        repository.loadCities(object : Callback<List<City>> {

            override fun onFailure(call: Call<List<City>>, t: Throwable) {
                presenter.onCitiesLoadFailure(t)
            }

            override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                val citiesResponse = response.body()

                if (citiesResponse != null) {
                    presenter.onCitiesLoadSuccess(citiesResponse)
                } else {
                    presenter.onCitiesLoadFailure(Throwable("Cities response is null"))
                }
            }
        })
    }
}