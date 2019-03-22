package exe.weazy.kudago.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import exe.weazy.kudago.R
import exe.weazy.kudago.entity.City

class CitiesRecyclerViewAdapter(private val items : List<City>) : RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder>() {

    var onItemClick: ((City) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_city, p0, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val item = items[p1]

        p0.title.text = item.title
        if (item.checked) p0.checked.visibility = View.VISIBLE
        else p0.checked.visibility = View.GONE
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