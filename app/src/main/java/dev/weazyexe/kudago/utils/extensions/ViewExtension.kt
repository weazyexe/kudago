package dev.weazyexe.kudago.utils.extensions

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding

/**
 * Обработать инсеты на [View] через установку паддинга
 */
fun View.setPaddingWithInsets(
    insetType: Int
) {
    val oldPaddingLeft = paddingLeft
    val oldPaddingTop = paddingTop
    val oldPaddingRight = paddingRight
    val oldPaddingBottom = paddingBottom

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val inset = insets.getInsets(insetType)
        view.updatePadding(
            left = oldPaddingLeft + inset.left,
            top = oldPaddingTop + inset.top,
            right = oldPaddingRight + inset.right,
            bottom = oldPaddingBottom + inset.bottom
        )

        insets
    }
}
