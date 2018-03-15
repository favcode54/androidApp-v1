package org.favcode54.favcode54.network
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * Created by Wilberforce on 1/25/18 at 11:04 PM.
 */
class VolleySingleton (private val sContext: Context) {
    private var mRequestQueue: RequestQueue? = null

    // getApplicationContext() is key, it keeps you from leaking the
    // Activity or BroadcastReceiver if someone passes one in.
    val requestQueue: RequestQueue?
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(sContext.applicationContext)
            }
            return mRequestQueue
        }

    init {
        this.mRequestQueue = requestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue?.add(req)
    }

    companion object {
        private var sVolleySingleton: VolleySingleton? = null

        @Synchronized
        fun getInstance(context: Context): VolleySingleton {
            if (sVolleySingleton == null) {
                sVolleySingleton = VolleySingleton(context)
            }
            return sVolleySingleton as VolleySingleton
        }
    }

}