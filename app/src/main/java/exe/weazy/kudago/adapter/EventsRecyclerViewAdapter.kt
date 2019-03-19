package exe.weazy.kudago.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import exe.weazy.kudago.activity.EventActivity
import exe.weazy.kudago.R
import exe.weazy.kudago.entity.Event
import exe.weazy.kudago.enums.ItemClickListener

class EventsRecyclerViewAdapter(private val items : List<Event>) : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.card_event, p0, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = items[p1]

        p0.title.text = item.title
        p0.shortDescription.text = item.shortDescription
        p0.dates.text = item.dates
        p0.place.text = item.place
        p0.price.text = item.price

        Glide.with(p0.eventLayout).load(item.imageUrls[0]).into(p0.image)

        p0.itemClickListener = object : ItemClickListener {
            override fun onClick(view: View?, position: Int) {
                val intent = Intent(view?.context, EventActivity::class.java)
                startActivity(view!!.context, intent, null)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var eventLayout : FrameLayout
        var title : TextView
        var shortDescription : TextView
        var image : ImageView
        var place : TextView
        var dates : TextView
        var price : TextView

        lateinit var itemClickListener : ItemClickListener

        init {
            super.itemView
            eventLayout = itemView.findViewById(R.id.event_layout)
            title = itemView.findViewById(R.id.card_header)
            shortDescription = itemView.findViewById(R.id.card_desc)
            image = itemView.findViewById(R.id.card_image)
            place = itemView.findViewById(R.id.card_location)
            dates = itemView.findViewById(R.id.card_date)
            price = itemView.findViewById(R.id.card_cost)

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClickListener.onClick(v, adapterPosition)
        }
    }
}