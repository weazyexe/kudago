package dev.weazyexe.kudago.arch

import dev.weazyexe.kudago.entity.City

interface CitiesContract {
    interface View {
        fun showLoading()
        fun showError()
        fun showCities(cities: List<City>)
    }

    interface Presenter {
        fun attach(view : View)
        fun fetchCities(city: City)
    }

    interface Model {
        fun fetchCities()
    }

    interface LoadingListener {
        fun onCitiesLoadSuccess(cities : List<City>)
        fun onCitiesLoadFailure(t : Throwable)
    }
}