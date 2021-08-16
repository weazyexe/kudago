package dev.weazyexe.kudago.ui.screen.cities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.weazyexe.kudago.R
import dev.weazyexe.kudago.databinding.ViewHolderCityBinding
import dev.weazyexe.kudago.domain.city.City

/**
 * Адаптер для вывода списка городов
 */
class CitiesAdapter(
    private val onItemClick: (City) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    private val items = mutableListOf<CityUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_city, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitList(items: List<CityUiModel>) {
        val diffUtilResult = DiffUtil.calculateDiff(CitiesDiffUtil(this.items, items))

        this.items.clear()
        this.items.addAll(items)

        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ViewHolderCityBinding.bind(view)

        fun bind(model: CityUiModel) {
            binding.root.setOnClickListener {
                onItemClick(model.city)
            }

            with(binding) {
                titleTv.text = model.city.title
                checkIv.isVisible = model.isChecked
            }
        }
    }
}