package dev.weazyexe.kudago.domain.event

/**
 * Объект с данными о месте проведения события
 */
data class Place(
    val title: String,
    val address: String,
    val lat: Double?,
    val lng: Double?
)