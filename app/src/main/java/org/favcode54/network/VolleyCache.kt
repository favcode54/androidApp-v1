package org.favcode54.favcode54.network

import com.android.volley.Cache
import com.android.volley.NetworkResponse
import com.android.volley.toolbox.HttpHeaderParser



/**
 * Created by Wilberforce on 1/26/18 at 11:24 AM.
 */
object VolleyCache {
    /**
     * Customize Volley cache behaviour per request
     * @param response The raw network response
     * @param cacheDuration How long you want the cache to last (in milliseconds)
     */
    fun parseIgnoreCacheHeaders(response: NetworkResponse, cacheDuration: Long): Cache.Entry {
        val now = System.currentTimeMillis()

        val headers = response.headers
        var serverDate: Long = 0
        val serverETag: String?
        val headerValue: String?

        headerValue = headers["Date"]
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue)
        }

        serverETag = headers["ETag"]

        val cacheHitButRefreshed = (3 * 60 * 1000).toLong() // in 3 minutes cache will be hit, but also refreshed on background
        val softExpire = now + cacheHitButRefreshed
        val ttl = now + cacheDuration

        val entry = Cache.Entry()
        entry.data = response.data
        entry.etag = serverETag
        entry.softTtl = softExpire
        entry.ttl = ttl
        entry.serverDate = serverDate
        entry.responseHeaders = headers

        return entry
    }
}