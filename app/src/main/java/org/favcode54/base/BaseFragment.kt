package org.favcode54

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.widget.TextView
import org.favcode54.blog.AppController

/**
 * Created by Wilberforce on 3/15/18 at 6:46 AM.
 */
@SuppressLint("Registered")
open class BaseFragment : Fragment() {

    fun applyFont(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            val view1 = viewGroup.getChildAt(i)
            if (view1 is TextView) {
                view1.typeface = AppController.customFont
            } else if (view1 is ViewGroup) {
                applyFont(view1)
            }
        }
    }



}