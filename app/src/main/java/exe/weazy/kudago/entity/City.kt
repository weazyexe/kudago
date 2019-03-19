package exe.weazy.kudago.entity

import com.google.gson.annotations.SerializedName

class City (
    @SerializedName("slug")
    var slug : String,

    @SerializedName("name")
    var title : String
)