package dev.weazyexe.kudago.ui.screen.event.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import dev.weazyexe.kudago.R

class EventImagesAdapter(private var images: List<String>) : PagerAdapter() {

    override fun isViewFromObject(p0: View, p1: Any) = p0 == p1

    override fun getCount() = images.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(R.drawable.ic_image_placeholder)
        container.addView(imageView)

        Glide.with(imageView)
            .load(images[position])
            .error(R.drawable.ic_image_placeholder)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(imageView)

        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}