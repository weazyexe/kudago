package dev.weazyexe.kudago.ui.screen.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.weazyexe.kudago.R
import dev.weazyexe.kudago.domain.event.Event
import dev.weazyexe.kudago.util.datesToString
import dev.weazyexe.kudago.util.placeToString

class EventsAdapter(
    private var items: MutableList<Event>
) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    var onItemClick: ((Event) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.card_event, p0, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title.uppercase()
        holder.shortDescription.text = item.shortDescription

        if (item.startDate != null || item.endDate != null) holder.dates.text =
            datesToString(item.startDate, item.endDate)
        else holder.datesLayout.isVisible = false

        if (item.place != null) holder.place.text = placeToString(item.place)
        else holder.placeLayout.isVisible = false

        if (item.price != "") holder.price.text = item.price
        else holder.priceLayout.isVisible = false

        if (item.images.isNotEmpty()) {
            Glide.with(holder.eventLayout)
                .load(item.images.first())
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.image)
        }
    }

    fun setData(items: List<Event>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var eventLayout: FrameLayout
        var title: TextView
        var shortDescription: TextView
        var image: ImageView
        var place: TextView
        var dates: TextView
        var price: TextView
        var datesLayout: LinearLayout
        var placeLayout: LinearLayout
        var priceLayout: LinearLayout

        init {
            super.itemView
            eventLayout = itemView.findViewById(R.id.event_layout)
            title = itemView.findViewById(R.id.card_header)
            shortDescription = itemView.findViewById(R.id.card_desc)
            image = itemView.findViewById(R.id.card_image)
            place = itemView.findViewById(R.id.card_location)
            dates = itemView.findViewById(R.id.card_date)
            price = itemView.findViewById(R.id.card_cost)
            datesLayout = itemView.findViewById(R.id.datesLayout)
            placeLayout = itemView.findViewById(R.id.placeLayout)
            priceLayout = itemView.findViewById(R.id.priceLayout)

            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }
}