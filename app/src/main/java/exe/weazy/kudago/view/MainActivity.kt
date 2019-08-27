package exe.weazy.kudago.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import exe.weazy.kudago.R
import exe.weazy.kudago.adapter.EventsAdapter
import exe.weazy.kudago.arch.MainContract
import exe.weazy.kudago.entity.City
import exe.weazy.kudago.entity.Event
import exe.weazy.kudago.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*


class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var viewModel : MainViewModel
    private lateinit var presenter : MainContract.Presenter

    private lateinit var adapter: EventsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val liveData = viewModel.getPresenter()
        liveData.observe(this, Observer {
            if (it != null) {
                presenter = it
                presenter.attach(this)

                val slug = getSharedPreferences(getString(R.string.app_package), Context.MODE_PRIVATE).getString("city", "msk")

                @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                presenter.fetchEvents(slug)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        initializeListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) return

        val city = data.extras?.getSerializable("city") as City
        showCity(city)
        presenter.fetchEvents(city.slug, isUpdate = true)
    }



    override fun showCity(city: City) {
        tv_city.text = city.title
        savePreferences()
    }

    override fun showError() {
        swipe_refresh_layout.isRefreshing = false

        loading_view.visibility = View.GONE
        event_cards_rv.visibility = View.GONE
        connection_failed.visibility = View.VISIBLE

        Snackbar.make(main_layout, getString(R.string.connection_failed_snackbar), Snackbar.LENGTH_LONG)
            .setAction(R.string.retry) {
                loading_view.visibility = View.VISIBLE
                connection_failed.visibility = View.GONE

                presenter.fetchEvents(presenter.city.slug)
            }.show()
    }

    override fun showEvents(events: List<Event>) {
        initializeAdapter(events)

        swipe_refresh_layout.isRefreshing = false
        loading_view.visibility = View.GONE
        event_cards_rv.visibility = View.VISIBLE
        connection_failed.visibility = View.GONE
    }

    override fun showLoading() {
        swipe_refresh_layout.isRefreshing = false

        loading_view.visibility = View.VISIBLE
        event_cards_rv.visibility = View.GONE
        connection_failed.visibility = View.GONE
    }



    private fun initializeListeners() {
        swipe_refresh_layout.setOnRefreshListener {
            presenter.fetchEvents(presenter.city.slug, isUpdate = true)
        }

        button_city.setOnClickListener {
            val intent = Intent(this, CityActivity::class.java)
            intent.putExtra("city", presenter.city)

            startActivityForResult(intent, 1)
        }
    }

    private fun initializeAdapter(events : List<Event>) {
        adapter = EventsAdapter(events)

        adapter.onItemClick = {
            val intent = Intent(this, EventActivity::class.java)

            intent.putExtra("event", it)
            startActivity(intent)
        }

        event_cards_rv.adapter = adapter
        event_cards_rv.layoutManager = LinearLayoutManager(this)
    }

    private fun savePreferences() {
        val sharedPreferences = getSharedPreferences(getString(R.string.app_package), Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("city", presenter.city.slug).apply()
    }
}
