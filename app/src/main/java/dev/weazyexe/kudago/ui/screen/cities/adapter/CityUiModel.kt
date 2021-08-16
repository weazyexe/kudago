package dev.weazyexe.kudago.ui.screen.cities.adapter

import dev.weazyexe.kudago.domain.city.City
import java.io.Serializable

/**
 * UI модель [City] для отображения в списке
 */
data class CityUiModel(
    val city: City,
    val isChecked: Boolean
) : Serializable