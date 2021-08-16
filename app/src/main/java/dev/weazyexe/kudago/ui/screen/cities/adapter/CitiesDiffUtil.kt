package dev.weazyexe.kudago.ui.screen.cities.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * DiffUtil для списка городов
 */
class CitiesDiffUtil(
    private val oldCities: List<CityUiModel>,
    private val newCities: List<CityUiModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldCities.size

    override fun getNewListSize(): Int = newCities.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCities[oldItemPosition].hashCode() == newCities[newItemPosition].hashCode()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldCities[oldItemPosition] == newCities[newItemPosition]
}