package org.favcode54.favcode54.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

/**
 * Created by Wilberforce on 2/4/18 at 6:01 PM.
 */

class CustomTypefaceSpan(family: String, private val newType: Typeface?) : TypefaceSpan(family) {

    override fun updateDrawState(ds: TextPaint) {
        applyCustomTypeFace(ds, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        applyCustomTypeFace(paint, newType)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface?) {
        if (tf == null) {
            return
        }
        val oldStyle: Int
        val old = paint.typeface
        oldStyle = old?.style ?: 0

        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = tf
    }
}
