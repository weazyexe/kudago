package exe.weazy.kudago.entity


class Event (
    var id : Int,
    var title : String,
    var shortDescription : String,
    var fullDescription : String,
    var place : String,
    var dates : String,
    var price : String,
    var imageUrls : ArrayList<String>,
    var coordinates: ArrayList<String>
)