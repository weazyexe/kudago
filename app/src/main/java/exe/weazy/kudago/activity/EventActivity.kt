package exe.weazy.kudago.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import exe.weazy.kudago.R
import exe.weazy.kudago.Tools
import exe.weazy.kudago.adapter.EventImagesPagerAdapter
import exe.weazy.kudago.entity.Event
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private var coordinates: List<Double> = ArrayList()
    private val tools: Tools by lazy(LazyThreadSafetyMode.NONE) { Tools(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        setData()
    }

    private fun setData() {
        val data = intent.extras.getParcelable("event") as Event

        event_title.text = data.title.toUpperCase()
        event_short_desc.text = data.shortDescription
        event_full_desc.text = tools.oneMoreEnter(data.fullDescription)

        if (data.place != "") event_place.text = data.place
        else full_event_place_layout.visibility = View.GONE

        if (data.dates != "") event_dates.text = data.dates
        else full_event_dates_layout.visibility = View.GONE

        if (data.price != "") event_price.text = data.price
        else full_event_price_layout.visibility = View.GONE

        var images: ArrayList<String>

        coordinates = data.coordinates

        if (coordinates.isNotEmpty()) createMapView()
        else mapLayout.visibility = View.GONE

        if (data.imageUrls.size > 0) {
            images = data.imageUrls
            val adapter = EventImagesPagerAdapter(this, images)
            images_viewpager.adapter = adapter

            circleIndicator.setViewPager(images_viewpager)
        } else {
            images_view.visibility = View.GONE
        }
    }

    fun goBack(v: View) {
        onBackPressed()
    }

    fun routeToEvent(v: View) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(
                "http://maps.google.com/maps?saddr=My+Location&daddr=" +
                        coordinates[0].toString() + "," + coordinates[1].toString()
            )
        )
        startActivity(intent)
    }

    private fun createMapView() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.event_coordinates) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0

        val coords = LatLng(coordinates[0], coordinates[1])

        googleMap?.addMarker(
            MarkerOptions().position(coords)
                .icon(tools.bitmapDescriptorFromVector(this, R.drawable.ic_map_mark))
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 15.0F))
    }

}
