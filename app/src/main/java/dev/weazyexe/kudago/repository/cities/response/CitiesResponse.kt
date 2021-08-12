package dev.weazyexe.kudago.repository.cities.response

import com.google.gson.annotations.SerializedName
import dev.weazyexe.kudago.app.core.Transformable
import dev.weazyexe.kudago.domain.city.City

/**
 * Dto города
 */
data class CityDto(
    @SerializedName("slug") val slug: String,
    @SerializedName("name") val name: String
) : Transformable<City> {

    override fun transform(): City = City(slug, name)
}



