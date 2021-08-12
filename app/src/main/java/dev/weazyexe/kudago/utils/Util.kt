package dev.weazyexe.kudago.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dev.weazyexe.kudago.domain.event.Place
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun placeToString(place : Place?) : String {
    if (place == null) return ""

    var result = ""

    if (place.title != "") result += place.title
    if (place.title != "" && place.address != "") result += ", "
    if (place.address != "") result += place.address

    return result
}

fun datesToString(startDate: Date?, endDate: Date?): String {
    val formatter = SimpleDateFormat("dd.MM")

    return when {
        startDate == null && endDate == null -> ""
        startDate != null && endDate == null -> formatter.format(startDate)
        startDate == null && endDate != null -> "до ${formatter.format(endDate)}"
        else -> "${formatter.format(startDate)} - ${formatter.format(endDate)}"
    }

}

fun coordinatesToList(place: Place?) : ArrayList<Double> {
    if (place == null) return ArrayList()

    var result = ArrayList<Double>()

    if (place.lat != null && place.lng != null) {
        result.add(place.lat)
        result.add(place.lng)
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