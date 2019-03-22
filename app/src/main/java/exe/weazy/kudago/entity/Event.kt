package exe.weazy.kudago.entity
import android.os.Parcel
import android.os.Parcelable

class Event : Parcelable {
    var id: Int = 0

    var title: String = ""

    var shortDescription: String = ""

    var fullDescription: String = ""

    var place: String = ""

    var price : String = ""

    var dates: String = ""

    var imageUrls = ArrayList<String>()

    var coordinates = ArrayList<Double>()

    constructor()

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()
        shortDescription = parcel.readString()
        fullDescription = parcel.readString()
        place = parcel.readString()
        price = parcel.readString()
        dates = parcel.readString()
        imageUrls = parcel.readArrayList(String.javaClass.classLoader) as ArrayList<String>
        coordinates = parcel.readArrayList(Double.javaClass.classLoader) as ArrayList<Double>
    }

    constructor(id: Int, title: String, shortDescription : String,
                fullDescription : String, place : String, dates : String, price : String,
                imageUrls : ArrayList<String>, coordinates : ArrayList<Double>) {
        this.id = id
        this.title = title
        this.shortDescription = shortDescription
        this.fullDescription = fullDescription
        this.place = place
        this.dates = dates
        this.price = price
        this.imageUrls = imageUrls
        this.coordinates = coordinates
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(shortDescription)
        parcel.writeString(fullDescription)
        parcel.writeString(place)
        parcel.writeString(price)
        parcel.writeString(dates)
        parcel.writeList(imageUrls)
        parcel.writeList(coordinates)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}