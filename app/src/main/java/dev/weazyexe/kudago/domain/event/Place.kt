package dev.weazyexe.kudago.domain.event

import java.io.Serializable

/**
 * Объект с данными о месте проведения события
 */
data class Place(
    val title: String,
    val address: String,
    val lat: Double?,
    val lng: Double?
) : Serializable