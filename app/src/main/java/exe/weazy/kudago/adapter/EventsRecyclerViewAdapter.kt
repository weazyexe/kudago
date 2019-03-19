package exe.weazy.kudago.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import exe.weazy.kudago.R
import exe.weazy.kudago.entity.Event

class EventsRecyclerViewAdapter(private val items : List<Event>) : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {

    var onItemClick: ((Event) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.card_event, p0, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = items[p1]

        p0.title.text = item.title
        p0.shortDescription.text = item.shortDescription

        if (item.dates != "") p0.dates.text = item.dates
        else p0.dates.visibility = View.GONE

        if (item.place != "") p0.place.text = item.place
        else p0.place.visibility = View.GONE

        if (item.price != "") p0.price.text = item.price
        else p0.price.visibility = View.GONE

        Glide.with(p0.eventLayout).load(item.imageUrls[0]).into(p0.image)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var eventLayout : FrameLayout
        var title : TextView
        var shortDescription : TextView
        var image : ImageView
        var place : TextView
        var dates : TextView
        var price : TextView

        init {
            super.itemView
            eventLayout = itemView.findViewById(R.id.event_layout)
            title = itemView.findViewById(R.id.card_header)
            shortDescription = itemView.findViewById(R.id.card_desc)
            image = itemView.findViewById(R.id.card_image)
            place = itemView.findViewById(R.id.card_location)
            dates = itemView.findViewById(R.id.card_date)
            price = itemView.findViewById(R.id.card_cost)

            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }
}