package dev.weazyexe.kudago.ui.screen.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.weazyexe.kudago.R
import dev.weazyexe.kudago.databinding.ViewHolderCardEventBinding
import dev.weazyexe.kudago.domain.event.Event
import dev.weazyexe.kudago.utils.datesToString
import dev.weazyexe.kudago.utils.placeToString

/**
 * Адаптер для вывода списка событий с пагинацией
 */
class EventsAdapter : PagingDataAdapter<Event, EventsAdapter.ViewHolder>(EventsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_card_event, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        private val binding by lazy { ViewHolderCardEventBinding.bind(parent) }

        fun bind(item: Event) = with(binding) {
            eventTitleTv.text = item.title.uppercase()
            eventDescriptionTv.text = item.shortDescription

            datesLayout.isVisible = item.startDate != null || item.endDate != null
            eventDatesTv.text = datesToString(item.startDate, item.endDate)

            placeLayout.isVisible = item.place != null
            eventPlaceTv.text = placeToString(item.place)

            priceLayout.isVisible = item.price.isNotEmpty()
            eventPriceTv.text = item.price

            if (item.images.isNotEmpty()) {
                Glide.with(eventLayout)
                    .load(item.images.first())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(eventImageIv)
            }
        }
    }
}