package exe.weazy.kudago

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import exe.weazy.kudago.adapter.EventsRecyclerViewAdapter
import exe.weazy.kudago.adapter.EventsRecyclerViewCard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var items = ArrayList<EventsRecyclerViewCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addItems()
        event_cards_rv.adapter = EventsRecyclerViewAdapter(items)
        event_cards_rv.layoutManager = LinearLayoutManager(this)
    }

    fun addItems() {
        items.add(EventsRecyclerViewCard("Девочка с персиками".toUpperCase(), "Правда персиков на фото не видно. Но они там есть, честно!", "Moscow", "1st of March, 02:00 PM", 0, R.drawable.girl1))
        items.add(EventsRecyclerViewCard("Девочка с короной".toUpperCase(), "Рандомный текст, вы не представляете насколько он бессмыслен. Невероятно, правда?", "Kirov", "13th of March, 12:00 AM", 1200, R.drawable.girl2))
        items.add(EventsRecyclerViewCard("Кот персик, стикеры такие".toUpperCase(), "Рыжий кот - Персик! Больше и слов не надо.", "New York", "2nd of March, 12:00 AM", 700, R.drawable.persik))
        items.add(EventsRecyclerViewCard("А это другой кот".toUpperCase(), "Черный кот - Салем. Да, тот самый. Правда круто?", "Solstheim", "4th of First Seed, 06:00 PM", 300, R.drawable.salem))
    }
}
