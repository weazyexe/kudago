package exe.weazy.kudago.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.support.annotation.NonNull
import android.view.ViewGroup
import android.R
import android.content.Context
import android.widget.ImageView



class EventImagesPagerAdapter : PagerAdapter {

    lateinit var context: Context
    lateinit var images : List<Int>

    constructor()

    constructor(context: Context, images: List<Int>) {
        this.images = images
        this.context = context
    }

    override fun isViewFromObject(p0: View, p1: Any) = p0 == p1

    override fun getCount() = images.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(images[position])
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}