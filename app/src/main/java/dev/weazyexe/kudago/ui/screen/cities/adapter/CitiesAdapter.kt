package dev.weazyexe.kudago.ui.screen.cities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.weazyexe.kudago.R
import dev.weazyexe.kudago.domain.city.City

class CitiesAdapter(private val items : List<City>) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    var onItemClick: ((City) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_city, p0, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = items[p1]

        p0.title.text = item.title
//        if (item.checked) p0.checked.visibility = View.VISIBLE
//        else p0.checked.visibility = View.GONE
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var checked : ImageView
        var title : TextView

        init {
            super.itemView
            checked = itemView.findViewById(R.id.item_city_checked)
            title = itemView.findViewById(R.id.item_city_title)

            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }
}