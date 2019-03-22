package exe.weazy.kudago.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import exe.weazy.kudago.R
import exe.weazy.kudago.adapter.CitiesRecyclerViewAdapter
import exe.weazy.kudago.entity.City
import exe.weazy.kudago.network.CitiesResponseCallback
import exe.weazy.kudago.network.RequestsRepository
import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() {

    var cities = ArrayList<City>()

    lateinit var currentCity : City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        currentCity = intent.extras.getSerializable("city") as City

        updateCities()
    }

    fun updateCities() {
        RequestsRepository.instance.getCities(object : CitiesResponseCallback<List<City>> {
            override fun onSuccess(apiResponse: List<City>) {
                apiResponse.forEach {

                    if (it == currentCity) it.checked = true

                    cities.add(
                        City(
                            it.slug,
                            it.title,
                            it.checked
                        )
                    )

                    createCitiesList()
                }
            }

            override fun onFailure(errorMessage: String) {
                cities_loading_view.visibility = View.GONE
            }
        })
    }

    fun createCitiesList() {
        val adapter = CitiesRecyclerViewAdapter(cities)

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
    }

    fun closeCities(v: View) {
        onBackPressed()
    }
}
