package exe.weazy.kudago.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import exe.weazy.kudago.R
import exe.weazy.kudago.adapter.EventsRecyclerViewAdapter
import exe.weazy.kudago.entity.Event
import exe.weazy.kudago.network.EventsRepository
import exe.weazy.kudago.network.EventsResponse
import exe.weazy.kudago.network.ResponseCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var events = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipe_refresh_layout.setOnRefreshListener {
            connection_failed.visibility = View.GONE
            event_cards_rv.visibility = View.GONE

            updateEvents()
        }

        updateEvents()
    }

    fun updateEvents() {
        EventsRepository.instance.getEvents(object : ResponseCallback<EventsResponse> {
            override fun onSuccess(apiResponse: EventsResponse) {
                apiResponse.events.forEach {
                    val currentImages = ArrayList<String>()

                    it.images.forEach { img ->
                        currentImages.add(img.image)
                    }

                    events.add(Event(
                        it.id,
                        it.title,
                        it.description,
                        it.fullDescription,
                        it.place.toString(),
                        it.dates.toString(),
                        it.price,
                        currentImages,
                        listOf(it.place.toString())
                    ))

                    createEventsFeed()
                }
            }

            override fun onFailure(errorMessage: String) {
                connection_failed.visibility = View.VISIBLE
                loading_view.visibility = View.GONE

                showSnackbarConnectionFailed()
            }
        })
    }

    fun createEventsFeed() {
        swipe_refresh_layout.isRefreshing = false

        event_cards_rv.visibility = View.VISIBLE
        event_cards_rv.adapter = EventsRecyclerViewAdapter(events)
        event_cards_rv.layoutManager = LinearLayoutManager(this)

        loading_view.visibility = View.GONE
    }

    fun showSnackbarConnectionFailed() {
        swipe_refresh_layout.isRefreshing = false

        Snackbar.make(main_layout, getString(R.string.connection_failed_snackbar), Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) {
                loading_view.visibility = View.VISIBLE
                connection_failed.visibility = View.GONE

                updateEvents()
            }.show()
    }
}
