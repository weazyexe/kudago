package dev.weazyexe.kudago.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dev.weazyexe.core.utils.EMPTY_STRING
import dev.weazyexe.core.utils.extensions.appendIf
import dev.weazyexe.kudago.domain.event.Place
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun placeToString(place: Place?): String {
    place ?: return EMPTY_STRING

    return StringBuilder()
        .appendIf(place.title.isNotEmpty(), place.title)
        .appendIf(place.title.isNotEmpty() && place.address.isNotEmpty(), ", ")
        .appendIf(place.address.isNotEmpty(), place.address)
        .toString()
}

fun datesToString(startDate: Date?, endDate: Date?): String {
    val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())

    val formattedStartDate = formatter.format(startDate ?: return EMPTY_STRING)
    val formattedEndDate = formatter.format(endDate ?: return formattedStartDate)

    return if (formattedStartDate == formattedEndDate) {
        formattedStartDate
    } else {
        "$formattedStartDate - $formattedEndDate"
    }
}

fun coordinatesToList(place: Place?): ArrayList<Double> {
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
        Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun oneMoreEnter(str: String): String {
    val paragraphs = str.split("\n")

    var result = ""

    paragraphs.forEach {
        result += it + "\n\n"
    }

    return result
}