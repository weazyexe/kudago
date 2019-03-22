package exe.weazy.kudago.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class City: @kotlin.jvm.Transient Serializable {
    @SerializedName("slug")
    var slug = ""

    @SerializedName("name")
    var title = ""

    var checked = false

    constructor(slug: String, title : String, checked : Boolean) {
        this.slug = slug
        this.title = title
        this.checked = checked
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is City) false
        else other.slug == this.slug && other.title == this.title
    }
}