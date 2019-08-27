package exe.weazy.kudago.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import exe.weazy.kudago.R
import exe.weazy.kudago.adapter.EventImagesAdapter
import exe.weazy.kudago.entity.Coordinates
import exe.weazy.kudago.entity.Event
import exe.weazy.kudago.entity.Image
import exe.weazy.kudago.util.bitmapDescriptorFromVector
import exe.weazy.kudago.util.datesToString
import exe.weazy.kudago.util.oneMoreEnter
import exe.weazy.kudago.util.placeToString
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private lateinit var coordinates: Coordinates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        setData()
    }

    private fun setData() {
        val data = intent.extras?.getSerializable("event") as Event

        event_title.text = data.title.toUpperCase()
        event_short_desc.text = data.shortDescription
        event_full_desc.text = oneMoreEnter(data.fullDescription)

        if (data.place != null) event_place.text = placeToString(data.place)
        else full_event_place_layout.visibility = View.GONE

        if (!data.dates.isNullOrEmpty()) event_dates.text = datesToString(data.dates[0].startDate, data.dates[0].endDate)
        else full_event_dates_layout.visibility = View.GONE

        if (data.price != "") event_price.text = data.price
        else full_event_price_layout.visibility = View.GONE

        val images: ArrayList<Image>

        if (data.place != null) {
            coordinates = data.place!!.coordinates
            if (coordinates.lat != null && coordinates.lon != null) {
                createMapView()
            } else {
                mapLayout.visibility = View.GONE
            }
        }

        if (data.images.size > 0) {
            images = data.images
            val adapter = EventImagesAdapter(images)
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
                        coordinates.lat + "," + coordinates.lon
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

        if (coordinates.lat != null && coordinates.lon != null) {
            val coords = LatLng(coordinates.lat!!, coordinates.lon!!)

            googleMap?.addMarker(
                MarkerOptions().position(coords)
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_map_mark))
            )
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 15.0F))
        }
    }

}
