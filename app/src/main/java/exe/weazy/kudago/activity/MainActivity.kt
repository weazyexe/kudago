package exe.weazy.kudago.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import exe.weazy.kudago.R
import exe.weazy.kudago.Tools
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

                    val tools = Tools()

                    events.add(Event(
                        it.id,
                        it.title,
                        it.description,
                        it.fullDescription,
                        tools.placeToString(it.place),
                        tools.datesToString(it.dates[0].start_date, it.dates[0].end_date),
                        it.price,
                        currentImages,
                        tools.coordinatesToList(it.place)
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

        val adapter = EventsRecyclerViewAdapter(events)

        adapter.onItemClick = {
            val intent = Intent(this, EventActivity::class.java)

            intent.putExtra("title", it.title)
            intent.putExtra("shortDesc", it.shortDescription)
            intent.putExtra("fullDesc", it.fullDescription)
            intent.putExtra("place", it.place)
            intent.putExtra("dates", it.dates)
            intent.putExtra("price", it.price)
            intent.putExtra("images", it.imageUrls)
            intent.putExtra("coordinates", it.coordinates)

            startActivity(intent)
        }

        event_cards_rv.adapter = adapter
        event_cards_rv.layoutManager = LinearLayoutManager(this)

        event_cards_rv.visibility = View.VISIBLE
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
