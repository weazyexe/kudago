package dev.weazyexe.kudago.domain.event

import java.io.Serializable
import java.util.*

/**
 * Объект с данными о событии
 */
data class Event(
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val place: Place?,
    val startDate: Date?,
    val endDate: Date?,
    val price: String,
    val images: List<String>
) : Serializable