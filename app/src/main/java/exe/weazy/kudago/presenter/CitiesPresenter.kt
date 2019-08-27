package exe.weazy.kudago.presenter

import exe.weazy.kudago.arch.CitiesContract
import exe.weazy.kudago.entity.City
import exe.weazy.kudago.model.CitiesModel

class CitiesPresenter : CitiesContract.Presenter, CitiesContract.LoadingListener {

    private val model = CitiesModel(this)
    private lateinit var view : CitiesContract.View
    private lateinit var cities : List<City>
    private lateinit var city : City



    override fun attach(view: CitiesContract.View) {
        this.view = view
    }

    override fun fetchCities(city : City) {
        if (!::cities.isInitialized) {
            view.showLoading()
            this.city = city
            model.fetchCities()
        } else {
            view.showCities(cities)
        }
    }



    override fun onCitiesLoadSuccess(cities: List<City>) {
        this.cities = cities
        this.cities.first { it == city }.checked = true

        view.showCities(this.cities)
    }

    override fun onCitiesLoadFailure(t: Throwable) {
        view.showError()
    }
}