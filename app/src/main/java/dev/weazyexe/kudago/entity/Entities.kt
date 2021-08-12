package dev.weazyexe.kudago.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable



data class CityResponse(
    @SerializedName("slug")
    val slug : String,
    @SerializedName("name")
    val name: String
)



data class City(
    @SerializedName("slug")
    var slug : String,
    @SerializedName("name")
    var title : String,
    var checked : Boolean = false
) : Serializable



