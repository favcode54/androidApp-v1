package org.favcode54

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.TextView
import org.favcode54.blog.AppController

/**
 * Created by Wilberforce on 3/15/18 at 6:46 AM.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

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


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


}