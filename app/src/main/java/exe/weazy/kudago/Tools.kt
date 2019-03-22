package exe.weazy.kudago

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import exe.weazy.kudago.network.Place
import java.text.DateFormatSymbols

class Tools {

    private lateinit var context: Context

    constructor()

    constructor(context: Context) {
        this.context = context
    }

    fun placeToString(place : Place?) : String {
        if (place == null) return ""

        var result = ""

        if (place.title != "") result += place.title
        if (place.title != "" && place.address != "") result += ", "
        if (place.address != "") result += place.address

        return result
    }

    fun datesToString(sDate: String?, eDate: String?): String {

        if (sDate == null || eDate == null) return ""

        var result = ""
        var sMonth = ""
        var sDay = ""
        var eMonth = ""
        var eDay = ""

        sMonth += sDate.substring(5, 7)
        sDay += sDate.substring(8)

        if (sDate != eDate) {
            eMonth += eDate.substring(5, 7)
            eDay += eDate.substring(8)
        }

        result += sDay.toInt().toString()

        if (sMonth != eMonth)
            result += " " + DateFormatSymbols().months[sMonth.toInt() - 1]

        if (sDate != eDate)
            result += " - " + eDay.toInt().toString() + " " + DateFormatSymbols().months[eMonth.toInt() - 1]

        return result
    }

    fun coordinatesToList(place: Place?) : ArrayList<Double> {
        if (place == null) return ArrayList()


        var result = ArrayList<Double>()

        if (place.coordinates.lat != null && place.coordinates.lon != null) {
            result.add(place.coordinates.lat)
            result.add(place.coordinates.lon)
        }

        return result
    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun oneMoreEnter(str : String) : String {
        val paragraphs = str.split("\n")

        var result = ""

        paragraphs.forEach {
            result += it + "\n\n"
        }

        return result
    }
}