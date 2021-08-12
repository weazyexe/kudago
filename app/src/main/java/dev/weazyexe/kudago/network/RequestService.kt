package dev.weazyexe.kudago.network

import dev.weazyexe.kudago.entity.City
import dev.weazyexe.kudago.entity.CityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RequestService {

    @GET("locations/?lang=ru")
    fun getCities() : Call<List<City>>

    @GET("locations/{location}/?fields=name,slug")
    fun getCityBySlug(@Path("location") slug: String) : Call<CityResponse>
}