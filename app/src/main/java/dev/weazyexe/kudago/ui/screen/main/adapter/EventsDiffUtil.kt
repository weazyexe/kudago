package dev.weazyexe.kudago.ui.screen.main.adapter

import androidx.recyclerview.widget.DiffUtil
import dev.weazyexe.kudago.domain.event.Event

/**
 * DiffUtil для [EventsAdapter]
 */
class EventsDiffUtil : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem.hashCode() == newItem.hashCode()

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem == newItem
}