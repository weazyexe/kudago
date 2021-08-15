package dev.weazyexe.kudago.repository.cities

import dev.weazyexe.kudago.repository.cities.response.CityDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API городов
 */
interface CitiesApi {

    /**
     * Получение списка всех городов
     */
    @GET("locations/")
    suspend fun getCities(
        @Query("lang") language: String = "ru"
    ): List<CityDto>

    /**
     * Получение города по его [slug]
     */
    @GET("locations/{location}/")
    suspend fun getCityBySlug(
        @Path("location") slug: String,
        @Query("fields") fields: String = "name,slug"
    ): CityDto
}