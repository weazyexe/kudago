package dev.weazyexe.kudago.domain.city

import java.io.Serializable

/**
 * Объект города
 */
data class City(
    val slug: String,
    val title: String
) : Serializable