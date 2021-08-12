package dev.weazyexe.kudago.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dev.weazyexe.kudago.R

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        setData()
    }

    private fun setData() {


    }

    fun goBack(v: View) {
        onBackPressed()
    }

    fun routeToEvent(v: View) {

    }

    private fun createMapView() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.event_coordinates) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {

    }

}
