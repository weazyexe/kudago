package exe.weazy.kudago.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import exe.weazy.kudago.R
import exe.weazy.kudago.adapter.CitiesAdapter
import exe.weazy.kudago.arch.CitiesContract
import exe.weazy.kudago.entity.City
import exe.weazy.kudago.viewmodel.CitiesViewModel

import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity(), CitiesContract.View {

    private lateinit var viewModel: CitiesViewModel
    private lateinit var presenter : CitiesContract.Presenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        val currentCity = intent.extras?.getSerializable("city") as City

        viewModel = ViewModelProviders.of(this).get(CitiesViewModel::class.java)
        val liveData = viewModel.getPresenter()
        liveData.observe(this, Observer {
            if (it != null) {
                presenter = it
                presenter.attach(this)
                presenter.fetchCities(currentCity)
            }
        })
    }

    override fun showCities(cities: List<City>) {
        val adapter = CitiesAdapter(cities)

        adapter.onItemClick = {
            val returnedIntent = Intent()
            returnedIntent.putExtra("city", it)
            setResult(Activity.RESULT_OK, returnedIntent)
            finish()
        }

        city_list_rv.adapter = adapter
        city_list_rv.layoutManager = LinearLayoutManager(this)

        city_list_rv.visibility = View.VISIBLE
        cities_loading_view.visibility = View.GONE
        connection_failed.visibility = View.GONE
    }

    override fun showError() {
        cities_loading_view.visibility = View.GONE
        city_list_rv.visibility = View.GONE
        connection_failed.visibility = View.VISIBLE
    }

    override fun showLoading() {
        cities_loading_view.visibility = View.VISIBLE
        city_list_rv.visibility = View.GONE
        connection_failed.visibility = View.GONE
    }



    fun closeCities(v: View) {
        onBackPressed()
    }
}
