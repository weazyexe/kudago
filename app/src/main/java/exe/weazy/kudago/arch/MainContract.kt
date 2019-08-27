package exe.weazy.kudago.arch

import exe.weazy.kudago.entity.City
import exe.weazy.kudago.entity.Event

class MainContract {

    interface View {
        fun showEvents(events : List<Event>)
        fun showCity(city : City)
        fun showLoading()
        fun showError()
    }

    interface Presenter {
        var city : City
        var cities : MutableList<City>
        var events : MutableList<Event>

        fun attach(view : View)
        fun fetchEvents(slug: String = "msk", isUpdate : Boolean = false)
    }

    interface Model {
        fun fetchEvents(slug : String)
        fun fetchCities()
    }

    interface LoadingListener {
        fun onEventsLoadSuccess(events: List<Event>)
        fun onEventsLoadFailure(t: Throwable)

        fun onCitiesLoadSuccess(cities: List<City>)
        fun onCitiesLoadFailure(t : Throwable)
    }
}