package exe.weazy.kudago

import exe.weazy.kudago.network.Date
import exe.weazy.kudago.network.Place
import java.text.DateFormatSymbols

class Tools {
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

    fun coordinatesToList(place: Place?) : ArrayList<String> {
        if (place == null) return ArrayList()


        var result = ArrayList<String>()

        if (place.coordinates.lat != null && place.coordinates.lon != null) {
            result.add(place.coordinates.lat.toString())
            result.add(place.coordinates.lon.toString())
        }

        return result
    }
}