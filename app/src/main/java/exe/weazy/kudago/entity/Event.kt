package exe.weazy.kudago.entity
import java.io.Serializable

class Event : @Transient Serializable {
    var id: Int = 0

    var title: String = ""

    var shortDescription: String = ""

    var fullDescription: String = ""

    var place: String = ""

    var price : String = ""

    var dates: String = ""

    var imageUrls = ArrayList<String>()

    var coordinates = ArrayList<Double>()

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
}