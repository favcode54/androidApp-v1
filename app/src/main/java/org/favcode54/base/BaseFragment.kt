package org.favcode54.favcode54.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import org.favcode54.favcode54.R
import org.favcode54.favcode54.base.App


/**
 * Created by Wilberforce on 1/26/18 at 12:48 AM.
 */

open class BaseFragment : Fragment() {

    override fun onDestroy() {
        super.onDestroy()
        App.refWatcher.watch(this)
        try {
            unRegisterNetworkChanges()
        } catch (e: IllegalArgumentException) {
            // Do nothing
        }
    }


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


    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun unRegisterNetworkChanges() {
        activity!!.unregisterReceiver(scanUpdateReceiver)
    }

    private val scanUpdateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Note: You can also use Connectivity.isConnected(activity!!)
            val noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
            if (noConnectivity) {
                phoneDisconnectedToInternet()
            } else {
                phoneConnectedToInternet()
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

    open fun phoneConnectedToInternet() {

    }

    open fun phoneDisconnectedToInternet() {

    }


}
