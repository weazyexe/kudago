package exe.weazy.kudago.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import exe.weazy.kudago.EventsViewModel
import exe.weazy.kudago.R
import exe.weazy.kudago.Tools
import exe.weazy.kudago.adapter.EventsRecyclerViewAdapter
import exe.weazy.kudago.entity.City
import exe.weazy.kudago.entity.Event
import exe.weazy.kudago.network.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : AppCompatActivity() {

    private val eventViewModel by lazy { ViewModelProviders.of(this).get(EventsViewModel::class.java) }

    companion object {
        var events = ArrayList<Event>()
    }

    lateinit var city: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCurrentCity()

        swipe_refresh_layout.setOnRefreshListener {
            connection_failed.visibility = View.GONE

            updateEvents()
        }

        updateEvents()
    }

    private fun updateEvents() {
        events = ArrayList()

        RequestsRepository.instance.getEvents(city.slug, object : EventsResponseCallback<EventsResponse> {
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

            intent.putExtra("event", it)
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

    fun chooseCity(v: View) {
        val intent = Intent(this, CityActivity::class.java)
        intent.putExtra("city", city)

        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) return

        city = data.extras.getSerializable("city") as City

        tv_city.text = city.title

        savePreferences()

        updateEvents()

        loading_view.visibility = View.VISIBLE
        event_cards_rv.visibility = View.GONE
    }

    private fun savePreferences() {
        val sharedPreferences = getSharedPreferences(getString(R.string.app_package), Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("city", city.slug).commit()
    }


    private fun getCurrentCity() {
        val slug = getSharedPreferences(getString(R.string.app_package), Context.MODE_PRIVATE).getString("city", "msk")

        city = City()
        city.slug = slug

        RequestsRepository.instance.getCityBySlug(slug, object : CityResponseCallback<CityResponse> {
            override fun onSuccess(apiResponse: CityResponse) {
                city.title = apiResponse.name
                city.checked = true
                tv_city.text = city.title
            }

            override fun onFailure(error: String) {
                city.title = "Москва"
                city.slug = "msk"
                city.checked = true
                tv_city.text = city.title
            }
        })
    }
}
