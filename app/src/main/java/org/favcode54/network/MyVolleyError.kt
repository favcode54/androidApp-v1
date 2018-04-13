package org.favcode54.favcode54.network

import android.content.Context
import com.android.volley.VolleyError
import java.lang.ref.WeakReference

/**
 * Created by Wilberforce on 1/25/18 at 11:10 PM.
 */
object MyVolleyError {

    fun errorMessage(error: VolleyError?, activity: Context?): String? {
        val weakContext = WeakReference(activity)
        val context = weakContext.get()
        var errorString: String? = null
        if (context != null && error != null) {

            /** errorString = when (error) {
                is TimeoutError -> context.getString(R.string.connect_time_o)
                is NoConnectionError -> context.getString(R.string.no_connect_err)
                is AuthFailureError -> context.getString(R.string.auth_error)
                is ServerError -> context.getString(R.string.server_error)
                is NetworkError -> context.getString(R.string.network_error)
                is ParseError -> context.getString(R.string.parse_error)
                else -> context.getString(R.string.sth_wrong_try_again)
           **/
        }


        return errorString
    }
}