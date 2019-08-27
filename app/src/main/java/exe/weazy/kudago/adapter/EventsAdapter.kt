package exe.weazy.kudago.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import exe.weazy.kudago.R
import exe.weazy.kudago.entity.Event
import exe.weazy.kudago.util.datesToString
import exe.weazy.kudago.util.placeToString

class EventsAdapter(private val items : List<Event>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    var onItemClick: ((Event) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) : ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.card_event, p0, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title.toUpperCase()
        holder.shortDescription.text = item.shortDescription

        if (!item.dates.isNullOrEmpty()) holder.dates.text = datesToString(item.dates[0].startDate, item.dates[0].endDate)
        else holder.datesLayout.visibility = View.GONE

        if (item.place != null) holder.place.text = placeToString(item.place)
        else holder.placeLayout.visibility = View.GONE

        if (item.price != "") holder.price.text = item.price
        else holder.priceLayout.visibility = View.GONE

        Glide.with(holder.eventLayout)
            .load(item.images[0].image)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(holder.image)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var eventLayout : FrameLayout
        var title : TextView
        var shortDescription : TextView
        var image : ImageView
        var place : TextView
        var dates : TextView
        var price : TextView
        var datesLayout : LinearLayout
        var placeLayout : LinearLayout
        var priceLayout : LinearLayout

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