package exe.weazy.kudago.presenter

import exe.weazy.kudago.arch.MainContract
import exe.weazy.kudago.entity.City
import exe.weazy.kudago.entity.Event
import exe.weazy.kudago.model.MainModel

class MainPresenter : MainContract.Presenter, MainContract.LoadingListener {

    private val model = MainModel(this)
    private lateinit var view: MainContract.View

    override lateinit var events : MutableList<Event>
    override lateinit var cities : MutableList<City>

    override lateinit var city : City

    private var isCitiesLoaded = false
    private var isEventsLoaded = false



    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun fetchEvents(slug : String, isUpdate : Boolean) {
        if (isUpdate || !::events.isInitialized) {
            view.showLoading()
            city = City(slug, "", true)

            model.fetchEvents(slug)
            model.fetchCities()
        } else if (!events.isNullOrEmpty()) {
            view.showEvents(events)
            view.showCity(city)
        }
    }



    override fun onEventsLoadSuccess(events: List<Event>) {
        this.events = events as MutableList<Event>
        isEventsLoaded = true
        afterLoad()
    }

    override fun onEventsLoadFailure(t: Throwable) {
        view.showError()
    }

    override fun onCitiesLoadSuccess(cities: List<City>) {
        this.cities = cities as MutableList<City>
        city = cities.first { it.slug == city.slug }
        isCitiesLoaded = true
        afterLoad()
    }

    override fun onCitiesLoadFailure(t: Throwable) {
        view.showError()
    }

    private fun afterLoad() {
        if (isCitiesLoaded && isEventsLoaded) {
            view.showEvents(events)
            view.showCity(city)
        }
    }
}