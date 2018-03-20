package org.favcode54.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Cache.Entry;
import com.android.volley.toolbox.HttpHeaderParser;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class VolleyCache {

    static public Entry parseIgnoreCacheHeaders(NetworkResponse response, long cacheDuration) {
        Intrinsics.checkParameterIsNotNull(response, "response");
        long now = System.currentTimeMillis();
        Map headers = response.headers;
        long serverDate = 0L;
        String headerValue = (String)headers.get("Date");
        if(headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        String serverETag = (String)headers.get("ETag");
        long cacheHitButRefreshed = (long)180000;
        long softExpire = now + cacheHitButRefreshed;
        long ttl = now + cacheDuration;
        Entry entry = new Entry();
        entry.data = response.data;
        entry.etag = serverETag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;
        return entry;
    }

}
