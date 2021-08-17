package dev.weazyexe.kudago.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import dev.weazyexe.core.utils.EMPTY_STRING
import dev.weazyexe.kudago.R
import dev.weazyexe.kudago.databinding.ViewErrorBinding

/**
 * Вью с отображением ошибки
 */
class ErrorView constructor(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private val binding = ViewErrorBinding.inflate(LayoutInflater.from(context))

    var title: String = EMPTY_STRING
        set(value) {
            binding.titleTv.text = value
            field = value
        }

    var text: String = EMPTY_STRING
        set(value) {
            binding.textTv.text = value
            field = value
        }

    var icon: Drawable? = null
        set(value) {
            binding.iconIv.setImageDrawable(value)
            binding.iconIv.isVisible = value != null
            field = value
        }

    init {
        addView(binding.root)
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ErrorView, 0, 0).apply {
            title = getString(R.styleable.ErrorView_ev_title).orEmpty()
            text = getString(R.styleable.ErrorView_ev_text).orEmpty()
            icon = getDrawable(R.styleable.ErrorView_ev_icon)

            recycle()
        }
    }
}