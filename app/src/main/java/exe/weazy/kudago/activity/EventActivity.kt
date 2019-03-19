package exe.weazy.kudago.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import exe.weazy.kudago.R

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        /*val images = listOf(
            R.drawable.girl1,
            R.drawable.girl2,
            R.drawable.persik,
            R.drawable.salem
        )

        val adapter = EventImagesPagerAdapter(this, images)
        images_viewpager.adapter = adapter

        circleIndicator.setViewPager(images_viewpager)*/
    }
}
