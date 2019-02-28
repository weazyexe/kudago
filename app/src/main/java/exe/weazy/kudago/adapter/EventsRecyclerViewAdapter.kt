package exe.weazy.kudago.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import exe.weazy.kudago.R
import kotlinx.android.synthetic.main.event_card.view.*

class EventsRecyclerViewAdapter(private val items : ArrayList<EventsRecyclerViewCard>) : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.event_card, p0, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = items[p1]

        p0.header.text = item.header
        p0.description.text = item.description
        p0.image.setImageResource(item.image)
        p0.datetime.text = item.datetime
        p0.location.text = item.location
        if (item.price == 0) p0.price.text = "Бесплатно"
        else p0.price.text = item.price.toString() + " руб."
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var header : TextView
        var description : TextView
        var image : ImageView
        var location : TextView
        var datetime : TextView
        var price : TextView

        init {
            super.itemView
            header = itemView.findViewById(R.id.card_header)
            description = itemView.findViewById(R.id.card_desc)
            image = itemView.findViewById(R.id.card_image)
            location = itemView.findViewById(R.id.card_location)
            datetime = itemView.findViewById(R.id.card_date)
            price = itemView.findViewById(R.id.card_cost)
        }
    }
}