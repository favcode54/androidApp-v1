package org.favcode54

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

/**
 * Created by Wilberforce on 3/15/18 at 6:46 AM.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {


    fun applyFont(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            val view1 = viewGroup.getChildAt(i)
            if (view1 is TextView) {
                view1.typeface = App.customFont
            } else if (view1 is ViewGroup) {
                applyFont(view1)
            }
        }
    }

    fun applyFont(alertDialog: AlertDialog) {
        // Getting the view elements
        val textView = alertDialog.window.findViewById(android.R.id.message) as TextView
        val alertTitle = alertDialog.window.findViewById(R.id.alertTitle) as TextView
        val button1 = alertDialog.window.findViewById(android.R.id.button1) as Button
        val button2 = alertDialog.window.findViewById(android.R.id.button2) as Button

        // Setting font
        textView.typeface = App.customFont
        alertTitle.typeface = App.customFont
        button1.typeface = App.customFont
        button2.typeface = App.customFont
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun tintProgressBar(progressBar: ProgressBar) {
        progressBar.indeterminateDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN)
    }


}