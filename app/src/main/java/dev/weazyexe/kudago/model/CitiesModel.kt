package dev.weazyexe.kudago.model

import dev.weazyexe.kudago.arch.CitiesContract
import dev.weazyexe.kudago.entity.City
import dev.weazyexe.kudago.network.NetworkRepository
import dev.weazyexe.kudago.presenter.CitiesPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitiesModel(private val presenter : CitiesPresenter) : CitiesContract.Model {

    private val repository = NetworkRepository()

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