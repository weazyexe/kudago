package exe.weazy.kudago.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import exe.weazy.kudago.R


class EventImagesPagerAdapter : PagerAdapter {

    lateinit var context: Context
    lateinit var images : List<String>

    constructor()

    constructor(context: Context, images: List<String>) {
        this.context = context
        this.images = images
    }

    override fun isViewFromObject(p0: View, p1: Any) = p0 == p1

    override fun getCount() = images.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.drawable.ic_image_placeholder)
        container.addView(imageView)

        Picasso.get().load(images[position]).error(R.drawable.ic_image_placeholder).into(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}