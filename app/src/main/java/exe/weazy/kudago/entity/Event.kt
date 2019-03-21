package exe.weazy.kudago.entity
import java.io.Serializable

class Event : @Transient Serializable {
    var id: Int = 0
        private set

    var title: String = ""
        private set

    var shortDescription: String = ""
        private set

    var fullDescription: String = ""
        private set

    var place: String = ""
        private set

    var dates: String = ""
        private set

    var price: String = ""
        private set

    var imageUrls = ArrayList<String>()
        private set

    var coordinates = ArrayList<Double>()
        private set

    constructor()

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