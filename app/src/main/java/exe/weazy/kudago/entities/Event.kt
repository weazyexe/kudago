package exe.weazy.kudago.entities

import android.media.Image
import java.time.LocalDate

class Event (
    var id : Long,
    var publicationDate : LocalDate,
    var startDate : LocalDate,
    var endDate : LocalDate,
    var title : String,
    var slug : String,
    var place : String,
    var description : String,
    var bodyText : String,
    var categories : List<Category>,
    var tagline : String,
    var ageRestriction : String,
    var price : Int,
    var isFree : Boolean,
    var images : Image,
    var favoritesCount : Int,
    var commentsCount : Int,
    var siteUrl : String,
    var tags : String,
    var participants : String
)