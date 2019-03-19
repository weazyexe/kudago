package exe.weazy.kudago.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import exe.weazy.kudago.R
import exe.weazy.kudago.adapter.EventImagesPagerAdapter
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        setData()
    }

    fun setData() {
        val data = intent.extras

        event_title.text = data.getString("title")
        event_short_desc.text = data.getString("shortDesc")
        event_full_desc.text = data.getString("fullDesc")
        event_place.text = data.getString("place")
        event_dates.text = data.getString("dates")
        event_price.text = data.getString("price")

        val images = data.get("images") as ArrayList<String>

        if (images.size > 0) {
            val adapter = EventImagesPagerAdapter(this, images)
            images_viewpager.adapter = adapter

            circleIndicator.setViewPager(images_viewpager)
        }
    }

    fun goBack(v: View) {
        onBackPressed()
    }
}
